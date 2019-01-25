package beans;

//import beans.repository.EyeTrackerService;

import api.PathConstants;
import beans.services.DataService;
import org.jboss.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
/**
 * A bean that is responsible of listening to certain destination for incoming messages
 * from the API
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = PathConstants.INCOMING_DATA_QUEUE),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")

})
public class RawDataMDB extends MessageBean {
    private static final Logger logger = Logger.getLogger(RawDataMDB.class.getName());


    @Inject
    private DataService dataService;

    /**
     * This mehods implements the messageRecievd function
     * @param message
     */
    @Override
    protected void messageReceived(String message){
        logger.info("eyeTracker queue instance, messageReceived");
        dataService.processMessage(message);
    }

}


