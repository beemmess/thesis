package beans;

import org.jboss.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public abstract class MessageBean implements MessageListener {

    private static final Logger logger = Logger.getLogger(MessageBean.class.getName());

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


    protected abstract void messageReceived(String message);


}
