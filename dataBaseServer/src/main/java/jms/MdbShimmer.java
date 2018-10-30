package jms;

import jms.services.ShimmerService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.jboss.logging.Logger;


@MessageDriven(name = "MdbShimmer", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/exported/jms/queue/shimmerRaw"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})

public class MdbShimmer extends MessageBean {

    private static final Logger logger = Logger.getLogger(MdbShimmer.class.getName());


    @Inject
    private ShimmerService shimmerService;


    @Override
    protected void messageReceived(String message){
        logger.info("shimmer raw instance");
        shimmerService.setMessage(message);
    }





//    @Override
//    public void onMessage(Message message){
//        logger.info("onMessage");
//        try {
//            if (message instanceof TextMessage) {
//                logger.info("Textmessage instance");
//                shimmerService.setMessage(((TextMessage)message).getText());
//            }
//            else {
//                logger.warn("Wrong type of message");
//            }
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//    }
}
