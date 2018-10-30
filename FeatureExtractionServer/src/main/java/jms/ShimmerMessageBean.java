package jms;

//import jms.services.EyeTrackerService;
import api.JNDIPaths;
import jms.services.ShimmerService;
import org.jboss.logging.Logger;

import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.inject.Inject;
import javax.jms.*;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = JNDIPaths.SHIMMER_QUEUE),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")

})
public class ShimmerMessageBean extends MessageBean {
    private static final Logger logger = Logger.getLogger(jms.ShimmerMessageBean.class.getName());

    @Inject
    private ShimmerService shimmerService;


    @Override
    protected void messageReceived(String message){
        logger.info("shimmer queue instance, messageReceived");
        shimmerService.sendRawDataToDB(message);
    }


//    private String msgText;
//    @Override
//    public void onMessage(Message message) {
//        TextMessage msg;
//        logger.info("DO I COME HERE IN Shimmer MESSAGE BEAN");
//        try {
//            if (message instanceof TextMessage) {
//                msg = (TextMessage) message;
//                logger.info("before setMessage in shimmerService " + msg.getText());
//
//                shimmerService.sendRawDataToDB("TEST MESSAGE");
//            } else {
//                logger.warn("Message af wrong type: " + message.getClass().getName());
//            }
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//
//    }
}


