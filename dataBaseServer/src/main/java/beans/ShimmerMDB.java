package beans;

import beans.services.ShimmerService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;

import org.jboss.logging.Logger;


@MessageDriven(name = "ShimmerMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = JNDIPaths.SHIMMER_QUEUE),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "connectionParameters", propertyValue = "host=207.154.211.58;port=5445"),
        @ActivationConfigProperty(propertyName = "connectorClassName", propertyValue = "org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory")
})

public class ShimmerMDB extends MessageBean {

    private static final Logger logger = Logger.getLogger(ShimmerMDB.class.getName());


    @Inject
    private ShimmerService shimmerService;


    @Override
    protected String messageReceived(String message){
        logger.info("shimmer raw instance");
        return shimmerService.saveDataToDB(message);
    }

}
