package api.impl;

import api.ApiResponseMessage;
import api.JNDIPaths;
import jms.services.EyeTrackerService;
import jnr.ffi.annotations.In;
import messages.Message;
import org.jboss.logging.Logger;
import com.google.gson.Gson;


import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;

public class ApiServiceImpl {
    private static final Logger logger = Logger.getLogger(ApiServiceImpl.class.getName());
    private ConnectionFactory connectionFactory;
    private Destination destination;
    private Destination remoteDest;
    private JMSContext jmsContext;
    private QueueConnection queueConnection;
    private Connection remoteConnection;
    private Gson gson = new Gson();



    public ApiServiceImpl(String queue, String cf) {
        logger.info("something wrong or not?");
        try {
//            remoteCf = InitialContext.doLookup("jms/remoteCF");
//            remoteDest = InitialContext.doLookup("java:jboss/exported/jms/queue/ExampleQueue")

            connectionFactory = InitialContext.doLookup(cf);
            destination = InitialContext.doLookup(queue);

//            remoteCf = InitialContext.doLookup(JNDIPaths.POOLED_CONNECTION_FACTORY);
            remoteDest = InitialContext.doLookup(JNDIPaths.REMOTE_QUEUE);
        } catch (NamingException e) {
            logger.error(e);
        }
    }

    public Response response(messages.Message message) {
        logger.info("ApiServiceImpl response 1 log");
        return response(gson.toJson(message));
    }


    public Response response(String message) {

        logger.info("ApiServiceImpl response 2 log");
        QueueSession session = null;
        Session sessionRemote = null;
        Connection conn = null;

        try {
            session = session();
            MessageProducer producer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage(message);
            producer.send(textMessage);
            producer.close();




//            logger.info("remote session session");
//            sessionRemote = sessionRemote();
//            MessageProducer producerRemote = sessionRemote.createProducer(remoteDest);
//            TextMessage textMessageRemote = sessionRemote.createTextMessage(message);
//            producerRemote.send(textMessageRemote);
//            logger.info("message sent to remote queue");
//            producerRemote.close();



            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, textMessage.getText())).build();

        } catch (JMSException e) {
            return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage())).build();
        }




    }

    private QueueSession session() throws JMSException{
        queueConnection = (QueueConnection) connectionFactory.createConnection();
        QueueSession session = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        return session;
    }

}
