package beans;

import api.JNDIPaths;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class MessageBean implements MessageListener {

    private static final Logger logger = Logger.getLogger(MessageBean.class.getName());


    private ConnectionFactory connectionFactory;


    @SuppressWarnings("Duplicates")
    @Override
    public void onMessage(Message message){
        logger.info("onMessage");

        try {
            if (message instanceof TextMessage) {
                logger.info("Textmessage instance");
                String reply = messageReceived(((TextMessage)message).getText());

                Destination destination = null;
                try {
                    connectionFactory = InitialContext.doLookup(JNDIPaths.DATABASE_CONNECTION_FACTORY);
                    destination = InitialContext.doLookup(JNDIPaths.REPLY_QUEUE);
                } catch (NamingException e) {
                    e.printStackTrace();
                }

                QueueSession session = setUpSession();
                MessageProducer producer = session.createProducer(destination);
                TextMessage replyMessage = session.createTextMessage(reply);
                producer.send(replyMessage);

            }
            else {
                logger.warn("Wrong type of message");
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    private QueueSession setUpSession() throws JMSException {
        QueueConnection qc = (QueueConnection) connectionFactory.createConnection();
        return qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    protected abstract String messageReceived(String message);

}
