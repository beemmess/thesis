package beans;

import beans.services.EyetrackerService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
//import java.util.logging.Logger;
import org.jboss.logging.Logger;


@MessageDriven(name = "MdbEyetracker", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/exported/beans/queue/eyetrackerRaw"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.beans.Queue")
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

}
