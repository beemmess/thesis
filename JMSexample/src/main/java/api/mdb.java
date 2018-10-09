package api;


import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.*;
import javax.jms.MessageListener;
import java.util.logging.Logger;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/exported/jms/queue/test"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})

public class mdb implements MessageListener {

    private static final Logger logger = Logger.getLogger(mdb.class.getName());

    @Inject
    private Counter counter;

    private String msgText;

//    private BufferedReader br = null;
//    private String line = "";
//    private String cvsSplitBy = ",";

//    @Override
//    public void onMessage(Message msg){
//        logger.info("onMessage");
//        counter.setCount(counter.getCount() +1);
//        try {
//            if (msg instanceof TextMessage) {
//                counter.setMessage(((TextMessage)msg).getText());
//            }
//            else {
//                byte[] body = new byte[(int) ((BytesMessage) msg).getBodyLength()];
//                ((BytesMessage) msg).readBytes(body);
//                msgText = new String(body);
////                msgText = msg.toString();
//                counter.setMessage(msgText);
//            }
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//    }
//}

    @Override
    public void onMessage(Message msg){
        logger.info("onMessage");
        counter.setCount(counter.getCount() +1);
        try {
            if (msg instanceof TextMessage) {
                counter.setMessage(((TextMessage)msg).getText());
            }
            else {
                byte[] body = new byte[(int) ((BytesMessage) msg).getBodyLength()];
                ((BytesMessage) msg).readBytes(body);
                msgText = new String(body);
//                System.out.print(msgText);
//                msgText = msg.toString();
                counter.setMessage(msgText);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
