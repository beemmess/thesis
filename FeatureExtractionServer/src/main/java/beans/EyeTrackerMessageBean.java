package beans;

//import beans.services.EyeTrackerService;

import api.JNDIPaths;
import beans.services.EyeTrackerService;
import org.jboss.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = JNDIPaths.EYETRACKER_QUEUE),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.beans.Queue")

})
public class EyeTrackerMessageBean extends MessageBean {
    private static final Logger logger = Logger.getLogger(beans.EyeTrackerMessageBean.class.getName());


    @Inject
    private EyeTrackerService eyeTrackerService;

    @Override
    protected void messageReceived(String message){
        logger.info("eyeTracker queue instance, messageReceived");
        eyeTrackerService.sendRawDataToDB(message);
    }

}


