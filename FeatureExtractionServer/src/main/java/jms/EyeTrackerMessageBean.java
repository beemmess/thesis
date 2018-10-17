package jms;

import jms.services.EyeTrackerService;
import org.jboss.logging.Logger;

import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.jms.*;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/exported/jms/queue/eyetracker"),
})
public class EyeTrackerMessageBean implements MessageListener {
    private EyeTrackerService eyeTrackerService;

    private static final Logger logger = Logger.getLogger(jms.EyeTrackerMessageBean.class.getName());
    private String msgText;

    @Override
    public void onMessage(Message message) {
        TextMessage msg;
        logger.info("DO I COME HERE IN EYETRACKER MESSAGE BEAN");


        try {
//            message.setJMSMessageID("IDSomething");
            message.setJMSType("eyetracker");
            logger.info(message.getJMSType());
            if (message instanceof BytesMessage) {
                byte[] body = new byte[(int) ((BytesMessage) message).getBodyLength()];
                ((BytesMessage) message).readBytes(body);
                msgText = new String(body);
                logger.info(msgText);
            }
            else {
                logger.warn("Message af wrong type: " + message.getClass().getName());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}


