package beans;

import beans.services.DataService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import org.jboss.logging.Logger;

/**
 * A bean that is responsible of listening to certain destination for incoming messages
 * from the Processing Server
 */
@MessageDriven(name = "DataMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = PathConstants.DATABASE_QUEUE),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "connectionParameters", propertyValue = "host="+ PathConstants.REMOTE_SERVER_IP+";port="+ PathConstants.REMOTE_SERVER_JMS_PORT),
        @ActivationConfigProperty(propertyName = "connectorClassName", propertyValue = "org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory")
})
public class DataMDB extends MessageBean {

    private static final Logger logger = Logger.getLogger(DataMDB.class.getName());

    @Inject
    private DataService dataService;

    /**
     * This mehods implements the messageRecievd function
     * @param message
     * @return String to respond to the Processing Server
     */
    @Override
    protected String messageReceived(String message){
        return dataService.saveDataToDB(message);

    }

}
