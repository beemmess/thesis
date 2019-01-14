package beans.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jboss.logging.Logger;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;


public abstract class DataProcessService {
    private static final Logger logger = Logger.getLogger(DataProcessService.class.getName());

    private ConnectionFactory connectionFactory;
    private Destination destination;
    private QueueConnection con;


    public String postToFlask(String message,String url){
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(message);

            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);

            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            return json;
        } catch (IOException ex) {
            logger.info(ex);
            return null;
        }
    }


    public void sendDataToDestination(String message, String queue, String cf){
        logger.info("sending data to database");

        try {
            connectionFactory = InitialContext.doLookup(cf);
            destination = InitialContext.doLookup(queue);


        } catch (NamingException e) {
            logger.error(e);
        }

        try {

            QueueSession session = session();
            MessageProducer producer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage(message);
            producer.send(textMessage);

            producer.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }


    }

    private QueueSession session() throws JMSException{
        con = (QueueConnection) connectionFactory.createConnection();
        return con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }


}
