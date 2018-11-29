package beans;

//import beans.repository.EyeTrackerService;

import api.PathConstants;
import beans.services.EyetrackerService;
import org.jboss.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = PathConstants.EYETRACKER_RAW_QUEUE),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")

})
public class EyeTrackerMessageBean extends MessageBean {
    private static final Logger logger = Logger.getLogger(beans.EyeTrackerMessageBean.class.getName());


    @Inject
    private EyetrackerService eyeTrackerService;

    @Override
    protected void messageReceived(String message){
        logger.info("eyeTracker queue instance, messageReceived");
        eyeTrackerService.processMessage(message);
    }

}


