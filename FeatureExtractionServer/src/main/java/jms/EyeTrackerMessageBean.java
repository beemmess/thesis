package jms;

//import jms.services.EyeTrackerService;

import api.JNDIPaths;
import jms.services.EyeTrackerService;
import org.jboss.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = JNDIPaths.EYETRACKER_QUEUE),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")

})
public class EyeTrackerMessageBean extends MessageBean {
    private static final Logger logger = Logger.getLogger(jms.EyeTrackerMessageBean.class.getName());


    @Inject
    private EyeTrackerService eyeTrackerService;

    private String msgText;

    @Override
    protected void messageReceived(String message){
        logger.info("eyeTracker queue instance, messageReceived");
        eyeTrackerService.sendRawDataToDB(message);
    }



//    @Override
//    public void onMessage(Message message) {
//        TextMessage msg;
//        logger.info("onMessage");
//
//        try {
//            if (message instanceof TextMessage) {
//                msg = (TextMessage) message;
//                logger.info("before setMessage in eytrackerService " + msg.getText());
//
//                eyeTrackerService.sendRawDataToDB("TEST MESSAGE");
//            }
//            else{
//                logger.warn("Wrong type of message");
//
//            }
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//
//    }
}


