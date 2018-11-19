package beans.services;

import api.JNDIPaths;
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

    public void processReply(String message){
    replyManager.addReplyToList(message);

    replyManager.getCount();
    replyManager.getListSize();


    if(replyManager.getCount()==replyManager.getListSize()){
        Object[] array = replyManager.getArrayList();
        String respondMessage = StringUtils.join(array,",");

        logger.info("respond message in process reply " + respondMessage);

        try {
            connectionFactory = InitialContext.doLookup(JNDIPaths.INCOMING_DATA_CONNECTION_FACTORY);
            destination = InitialContext.doLookup(JNDIPaths.REST_REPLY_QUEUE);

            session = session();
            MessageProducer producer = session.createProducer(destination);
            TextMessage replyMessage = session.createTextMessage(respondMessage);
            producer.send(replyMessage);
        } catch (JMSException | NamingException e) {
            logger.error(e.toString());
        }

        replyManager.clearList();


        }
    }

    private QueueSession session() throws JMSException {
        con = (QueueConnection) connectionFactory.createConnection();
        return con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }



}
