package beans;

import api.JNDIPaths;
import beans.services.EyetrackerService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
//import java.util.logging.Logger;
import org.jboss.logging.Logger;


@MessageDriven(name = "EytrackerMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = JNDIPaths.EYETRACKER_QUEUE),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})

public class EyetrackerMDB extends MessageBean {

    private static final Logger logger = Logger.getLogger(EyetrackerMDB.class.getName());


    @Inject
    private EyetrackerService eyetrackerService;


    @Override
    protected String messageReceived(String message){
//        logger.info("eyeTracker raw queue instance, message: " + message);

        return eyetrackerService.saveDataToDB(message);

    }

}
