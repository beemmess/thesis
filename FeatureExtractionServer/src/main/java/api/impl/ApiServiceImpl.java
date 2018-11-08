package api.impl;

import api.ApiResponseMessage;
import org.jboss.logging.Logger;
import com.google.gson.Gson;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;

public class ApiServiceImpl {
    private static final Logger logger = Logger.getLogger(ApiServiceImpl.class.getName());
    private ConnectionFactory connectionFactory;
    private Destination destination;
    private QueueConnection queueConnection;
    private Gson gson = new Gson();



    public ApiServiceImpl(String queue, String cf) {
        logger.info("API service impl?");
        try {

            connectionFactory = InitialContext.doLookup(cf);
            destination = InitialContext.doLookup(queue);

        } catch (NamingException e) {
            logger.error(e);
        }
    }

    public Response response(model.Message message) {
        logger.info("ApiServiceImpl response 1 log");

        return response(gson.toJson(message));
    }

    @SuppressWarnings("Duplicates")
    public Response response(String message) {

        logger.info("ApiServiceImpl response 2 log");
        QueueSession session = null;


        try {
            session = session();
//            logger.info("string: " +message);
            MessageProducer producer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage(message);
            producer.send(textMessage);
            producer.close();

            return Response.ok().entity(new ApiResponseMessage(200, message)).build();

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
