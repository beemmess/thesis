package beans;

import api.PathConstants;
import beans.services.ReplyService;
import org.jboss.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;

/**
 * A bean that is responsible of listening to certain destination for incoming messages
 * from the Database Server
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = PathConstants.REPLY_QUEUE),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "connectionParameters", propertyValue = "host="+ PathConstants.REMOTE_SERVER_IP+";port="+ PathConstants.REMOTE_SERVER_JMS_PORT),

        @ActivationConfigProperty(propertyName = "connectorClassName", propertyValue = "org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory")
}, mappedName = "ReplyQueue")
public class ReplyMDB extends MessageBean {

    private static final Logger logger = Logger.getLogger(ReplyMDB.class.toString());

    @Inject
    private ReplyService replyService;

    /**
     * This mehods implements the messageRecievd function
     * @param message
     */
    @Override
    protected void messageReceived(String message) {
        replyService.processReply(message);
    }

}