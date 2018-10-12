package jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;
import java.util.logging.Logger;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/exported/jms/queue/test"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})

public class Mdb implements MessageListener {

    private static final Logger logger = Logger.getLogger(Mdb.class.getName());


    @Inject
    private Eyetracker eyetracker;

    private String msgText;


    @Override
    public void onMessage(Message msg){
        logger.info("onMessage");
        try {
            if (msg instanceof TextMessage) {
                eyetracker.setMessage(((TextMessage)msg).getText());
            }
            else {
                byte[] body = new byte[(int) ((BytesMessage) msg).getBodyLength()];
                ((BytesMessage) msg).readBytes(body);
                msgText = new String(body);
//                System.out.print(msgText);
//                msgText = msg.toString();
                eyetracker.setMessage(msgText);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
