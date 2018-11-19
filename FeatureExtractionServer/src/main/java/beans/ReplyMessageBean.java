package beans;

import api.JNDIPaths;
import beans.services.EyetrackerService;
import beans.services.ReplyService;
import org.jboss.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = JNDIPaths.REPLY_QUEUE),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "connectionParameters", propertyValue = "host=104.248.35.208;port=5445"),
        @ActivationConfigProperty(propertyName = "connectorClassName", propertyValue = "org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory")
}, mappedName = "ReplyQueue")
public class ReplyMessageBean extends MessageBean {

    private static final Logger logger = Logger.getLogger(ReplyMessageBean.class.toString());

    @Inject
    private ReplyService replyService;

    @Override
    protected void messageReceived(String message) {
        replyService.processReply(message);
    }

}