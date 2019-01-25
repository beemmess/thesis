package api.impl;

import api.ApiResponseMessage;
import api.PathConstants;
import org.jboss.logging.Logger;
import com.google.gson.Gson;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;


/**
 * A class that handles the reequest that are coming to the /api/data endpoint
 * this class will also send JMS messages to the RawDataMDB to process
 * the incoming data
 */
public class ApiServiceImpl {
    private static final Logger logger = Logger.getLogger(ApiServiceImpl.class.getName());
    private Destination destination;
    private Destination replyDest;
    private ConnectionFactory conFactory;
    private QueueConnection QueueCon;
    private Gson gson = new Gson();


    /**
     * A constructer that looks up the desired queues for destination and connection factory.
     * @param queue
     * @param cf
     */
    public ApiServiceImpl(String queue, String cf) {
        try {
            conFactory = InitialContext.doLookup(cf);
            destination = InitialContext.doLookup(queue);
            replyDest = InitialContext.doLookup(PathConstants.REST_REPLY_QUEUE);


        } catch (NamingException e) {
            logger.error(e);
        }
    }

    /**
     * A helper method to serilise the incoming JSON string
     * @param message
     * @return Response for the API request
     */
    public Response response(model.Message message) {
        return response(gson.toJson(message));
    }

    /**
     * A method that forwards the incoming API request to the RawDataMDB to further process the data
     * @param message
     * @return A response for the API request
     */
    public Response response(String message) {

        try {
            QueueSession session = session();
            MessageProducer producer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage(message);
            producer.send(textMessage);
            producer.close();


            MessageConsumer consumer = session.createConsumer(replyDest);
            QueueCon.start();
            textMessage = (TextMessage) consumer.receive();
            QueueCon.stop();
            consumer.close();

            session.close();
            QueueCon.close();

            return Response.ok().entity(new ApiResponseMessage(200, textMessage.getText())).build();

        } catch (JMSException e) {
            return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage())).build();
        }

    }

    /**
     * A helper method to generate a QueueSession
     * @return QueueSession
     * @throws JMSException
     */
    private QueueSession session() throws JMSException{
        QueueCon = (QueueConnection) conFactory.createConnection();
        return QueueCon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }

}
