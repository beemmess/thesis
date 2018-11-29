package beans;

//import beans.repository.EyeTrackerService;
import api.PathConstants;
import beans.services.ShimmerService;
import org.jboss.logging.Logger;

import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.inject.Inject;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = PathConstants.SHIMMER_RAW_QUEUE),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")

})
public class ShimmerMessageBean extends MessageBean {
    private static final Logger logger = Logger.getLogger(beans.ShimmerMessageBean.class.getName());

    @Inject
    private ShimmerService shimmerService;


    @Override
    protected void messageReceived(String message){
        logger.info("shimmer queue instance, messageReceived");
        shimmerService.processMessage(message);
    }

}


