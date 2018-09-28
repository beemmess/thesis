package api;


import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
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

    @Override
    public void onMessage(Message message){
        logger.info("onMessage");
        counter.setCount(counter.getCount() +1);
        try {
            counter.setMessage(message.getJMSMessageID());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
