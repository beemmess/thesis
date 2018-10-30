package jms;

import jms.services.EyetrackerService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;
//import java.util.logging.Logger;
import org.jboss.logging.Logger;


@MessageDriven(name = "MdbEyetracker", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/exported/jms/queue/eyetrackerRaw"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})

public class MdbEyetracker extends MessageBean {

    private static final Logger logger = Logger.getLogger(MdbEyetracker.class.getName());


    @Inject
    private EyetrackerService eyetrackerService;


    @Override
    protected void messageReceived(String message){
        logger.info("eyeTracker raw queue instance, message: " + message);
        eyetrackerService.setMessage(message);
    }



//    @Override
//    public void onMessage(Message message){
//        logger.info("onMessage");
//        try {
//            if (message instanceof TextMessage) {
//                logger.info("Textmessage instance");
//                eyetrackerService.setMessage(((TextMessage)message).getText());
//            }
//            else {
//                logger.warn("Wrong type of message");
//            }
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//    }
}
