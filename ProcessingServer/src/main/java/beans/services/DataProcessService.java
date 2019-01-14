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
//        System.out.println("post0");
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
//            System.out.println("post1");
            HttpPost request = new HttpPost(url);
//            System.out.println("post2");
            StringEntity params = new StringEntity(message);
//            System.out.println("post3");

            request.addHeader("content-type", "application/json");
//            System.out.println("post4");
            request.setEntity(params);
//            System.out.println("post5");
            HttpResponse result = httpClient.execute(request);
//            System.out.println("post6");

            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
//            System.out.println("post7");
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
//            System.out.println("sendData1");
            destination = InitialContext.doLookup(queue);
//            System.out.println("sendData2");


        } catch (NamingException e) {
            logger.error(e);
        }

        try {
//            System.out.println("sendData3");

            QueueSession session = session();
//            System.out.println("sendData4");

            MessageProducer producer = session.createProducer(destination);
//            System.out.println("sendData5");

            TextMessage textMessage = session.createTextMessage(message);
//            System.out.println("sendData6");

            producer.send(textMessage);
//            System.out.println("sendData7");

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
