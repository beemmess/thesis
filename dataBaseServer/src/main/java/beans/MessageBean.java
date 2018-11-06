package beans;

import org.jboss.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public abstract class MessageBean implements MessageListener {

    private static final Logger logger = Logger.getLogger(MessageBean.class.getName());


    @Override
    public void onMessage(Message message){
        logger.info("onMessage");

        try {
            if (message instanceof TextMessage) {
                logger.info("Textmessage instance");
                messageReceived(((TextMessage)message).getText());
            }
            else {
                logger.warn("Wrong type of message");
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    protected Boolean void messageReceived(String message);

}
