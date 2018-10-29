package jms;

//import jms.services.EyeTrackerService;

import jms.services.EyeTrackerService;
import org.jboss.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/exported/jms/queue/eyetracker"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")

})
public class EyeTrackerMessageBean implements MessageListener {


    @Inject
    private EyeTrackerService eyeTrackerService;

    private static final Logger logger = Logger.getLogger(jms.EyeTrackerMessageBean.class.getName());
    private String msgText;

    @Override
    public void onMessage(Message message) {
        TextMessage msg;
        logger.info("EYETRACKER MESSAGE BEAN");


        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                logger.info("before setMessage in eytrackerService " + msg.getText());

//                eyeTrackerService.sendMessageToDB(((TextMessage)message).getText());
                eyeTrackerService.sendMessageToDB("TEST MESSAGE");
            }
            else{
                byte[] body = new byte[(int) ((BytesMessage) message).getBodyLength()];
                ((BytesMessage) message).readBytes(body);
                msgText = new String(body);
                logger.info("before setMessage in eytrackerService " + msgText);
//                eyeTrackerService.createEyeTrackerService(msgText);
//                eyeTrackerService.sendMessageToDB(msgText);

            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}


