package beans.services;

import api.JNDIPaths;
import org.jboss.logging.Logger;
import reply.ReplyManager;

import javax.annotation.Resource;
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

    public void processReply(String message){
//    String values[] = null;
    replyManager.addToReplyList(message);


    if(replyManager.getCount()==replyManager.getListSize()){
        Object[] array = replyManager.getArrayList();
        StringBuilder respondMessage = new StringBuilder();
        for(Object reply: array){
            respondMessage.append(reply).append(",");
        }
        logger.info("respond message in process reply " + respondMessage);

        try {
            connectionFactory = InitialContext.doLookup(JNDIPaths.INCOMING_DATA_CONNECTION_FACTORY);
            destination = InitialContext.doLookup(JNDIPaths.REST_REPLY_QUEUE);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        try {
            session = setUpSession();
            MessageProducer producer = session.createProducer(destination);
            TextMessage replyMessage = session.createTextMessage(respondMessage.toString());
            producer.send(replyMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        replyManager.clearList();


        }
    }

    private QueueSession setUpSession() throws JMSException {
        QueueConnection qc = (QueueConnection) connectionFactory.createConnection();
        return qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }



}
