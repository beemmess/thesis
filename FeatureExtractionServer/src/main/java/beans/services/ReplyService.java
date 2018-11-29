package beans.services;

import api.PathConstants;
import com.google.gson.Gson;
import model.ReplyMessage;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import reply.ReplyManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Named
@ApplicationScoped
public class ReplyService extends DeviceService {
    private static final Logger logger = Logger.getLogger(ReplyService.class.getName());

    private ReplyManager replyManager = ReplyManager.getInstance();

    private ConnectionFactory connectionFactory;
    private Destination destination;
    private QueueSession session;
    private QueueConnection con;
    private Gson gson = new Gson();

    public void processReply(String message){
    replyManager.addReplyToList(message);


    if(replyManager.getCount()==replyManager.getListSize()){

        Object[] array = replyManager.getArrayList();
        String arrayMessage = StringUtils.join(array,"\t");
        String[] replyMsgList = arrayMessage.split("\t");
        String replyMessage = generateReplyMessage(replyMsgList);
        logger.info(replyMessage);
        try {
            connectionFactory = InitialContext.doLookup(PathConstants.INCOMING_DATA_CONNECTION_FACTORY);
            destination = InitialContext.doLookup(PathConstants.REST_REPLY_QUEUE);

            session = session();
            MessageProducer producer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage(replyMessage);
            producer.send(textMessage);
        } catch (JMSException | NamingException e) {
            logger.error(e.toString());
        }

        replyManager.clearList();


        }
    }


    private String generateReplyMessage(String[] list) {
        int len = list.length;
        String msg ="";
        int successCount = 0;
        for (String reply : list){
            ReplyMessage replyMessage = gson.fromJson(reply, ReplyMessage.class);
            Boolean success = replyMessage.getSucess();
            msg += replyMessage.getData()+ " " + replyMessage.getReplyMessage() +": "+ replyMessage.getSucess()+"\n";
            if(success){
                successCount +=1;
            }
        }
        if(successCount == len){
            return "All data has been sucessfully saved to database:\n" + msg;
        }
        return "Error in saving in database:\n" + msg;
    }

    private QueueSession session() throws JMSException {
        con = (QueueConnection) connectionFactory.createConnection();
        return con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }



}
