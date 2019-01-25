package beans;

import org.jboss.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * A message driven bean that is listening for messages
 */
public abstract class MessageBean implements MessageListener {

    private static final Logger logger = Logger.getLogger(MessageBean.class.getName());

    /**
     * This medhod listens for incoming messages, when message has been forwarded and information returned
     * then a JMS message is generated to reply to the Processing Server
     * @param message
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void onMessage(Message message){
        logger.info("onMessage");
        try {
            if (message instanceof TextMessage) {
                messageReceived(((TextMessage)message).getText());
            }
            else {
                logger.warn("Wrong type of message");
            }
        } catch (JMSException e) {
            logger.error(e.toString());
        }
    }

    /**
     * Handling the text message that is coming from the queue
     * @param message
     * @return a response in a String form to reply back to the Processing Server
     */
    protected abstract void messageReceived(String message);


}
