package beans;

import beans.services.EyetrackerService;
import org.jboss.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;

//import java.util.logging.Logger;


@MessageDriven(name = "MdbEyetrackerProcessed", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/exported/jms/queue/eyetrackerProcessed"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})

public class MdbEyetrackerProcessed extends MessageBean {

    private static final Logger logger = Logger.getLogger(MdbEyetrackerProcessed.class.getName());


    @Inject
    private EyetrackerService eyetrackerService;


    @Override
    protected void messageReceived(String message){
        logger.info("eyeTracker Processed queue instance, message ================: " + message);

        eyetrackerService.saveDataToDB(message);
    }

}
