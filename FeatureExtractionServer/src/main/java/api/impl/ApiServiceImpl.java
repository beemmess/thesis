package api.impl;

import api.ApiResponseMessage;
import api.JNDIPaths;
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
    private Destination replyDestination;
    private QueueConnection con;
    private Gson gson = new Gson();



    public ApiServiceImpl(String queue, String cf) {
        try {
            connectionFactory = InitialContext.doLookup(cf);
            destination = InitialContext.doLookup(queue);
            replyDestination = InitialContext.doLookup(JNDIPaths.REST_REPLY_QUEUE);


        } catch (NamingException e) {
            logger.error(e);
        }
    }

    public Response response(model.Message message) {
        return response(gson.toJson(message));
    }

    @SuppressWarnings("Duplicates")
    public Response response(String message) {
//


        try {
            QueueSession session = session();
            MessageProducer producer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage(message);
            producer.send(textMessage);
            producer.close();


            MessageConsumer consumer = session.createConsumer(replyDestination);
            con.start();
            textMessage = (TextMessage) consumer.receive();
            con.stop();
            consumer.close();

            session.close();
            con.close();

            return Response.ok().entity(new ApiResponseMessage(200, textMessage.getText())).build();

        } catch (JMSException e) {
            return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage())).build();
        }

    }

    private QueueSession session() throws JMSException{
        con = (QueueConnection) connectionFactory.createConnection();
        return con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }

}
