package beans;

import beans.services.ShimmerService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;

import org.jboss.logging.Logger;


@MessageDriven(name = "MdbShimmerRaw", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/exported/jms/queue/shimmerRaw"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})

public class MdbShimmerRaw extends MessageBean {

    private static final Logger logger = Logger.getLogger(MdbShimmerRaw.class.getName());


    @Inject
    private ShimmerService shimmerService;


    @Override
    protected void messageReceived(String message){
        logger.info("shimmer raw instance");
        shimmerService.saveDataToDB(message);
    }

}
