package beans;

import org.jboss.logging.Logger;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class MessageBean implements MessageListener {

    private static final Logger logger = Logger.getLogger(MessageBean.class.getName());


    private ConnectionFactory connectionFactory;
    private QueueConnection con;
    private Destination destination;


    @SuppressWarnings("Duplicates")
    @Override
    public void onMessage(Message message){
        logger.info("onMessage");

        try {
            if (message instanceof TextMessage) {
                logger.info("Textmessage instance");
                String reply = messageReceived(((TextMessage)message).getText());

                connectionFactory = InitialContext.doLookup(PathConstants.PROCESSING_SERVER_CONNECTION_FACTORY);
                destination = InitialContext.doLookup(PathConstants.REPLY_QUEUE);


                QueueSession session = session();
                MessageProducer producer = session.createProducer(destination);
                TextMessage replyMessage = session.createTextMessage(reply);
                producer.send(replyMessage);
                producer.close();

            }
            else {
                logger.warn("Wrong type of message");
            }
        } catch (JMSException | NamingException e) {
            logger.error(e.toString());
        }
    }


    private QueueSession session() throws JMSException {
        con = (QueueConnection) connectionFactory.createConnection();
        return con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    protected abstract String messageReceived(String message);

}
