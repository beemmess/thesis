package jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;
import java.util.logging.Logger;

@MessageDriven(name = "Mdb", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/exported/jms/queue/ExampleQueue"),
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
                logger.info("Textmessage instance");
                eyetracker.setMessage(((TextMessage)msg).getText());
            }
            else {
                byte[] body = new byte[(int) ((BytesMessage) msg).getBodyLength()];
                ((BytesMessage) msg).readBytes(body);
                msgText = new String(body);
                logger.info("MDB message recieved");
//                System.out.print(msgText);
//                msgText = msg.toString();
                eyetracker.setMessage(msgText);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
