package beans.services;

import api.PathConstants;
import com.google.gson.Gson;
import model.DataMessage;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jboss.logging.Logger;
import reply.ReplyManager;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;


public abstract class DataProcessService {
    private static final Logger logger = Logger.getLogger(DataProcessService.class.getName());

    private ConnectionFactory connectionFactory;
    private Destination destination;
    private QueueConnection con;

//    private ReplyManager replyManager = ReplyManager.getInstance();

//    private String queue = PathConstants.EYETRACKER_QUEUE;
//    private Gson gson = new Gson();
//
//    private String msg;
//    private String address = PathConstants.LOCAL_SERVER_IP;     // Local server ip address
//    private String port = PathConstants.PYTHON_WEB_CLIENT_PORT; // port of the python web client


//    private ReplyManager replyManager = ReplyManager.getInstance();



//    public void processMessage(String message){
//        DataMessage dataMessage = gson.fromJson(message, DataMessage.class);
//        String[] apiUrls = dataMessage.getApiUrl().split(",");
//        replyManager.clearList();
//        replyManager.clearCount();
//        replyManager.setCount(apiUrls.length+1);
//
//        sendDataToDB(message, queue);
//
//        for(String apiUrl : apiUrls){
//            String url = "http://" + address + ":" + port + apiUrl;
//            msg = postToFlask(message, url);
//            sendDataToDB(msg,queue);
//        }
//    }


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


    public void sendDataToDB(String message, String queue){
        logger.info("sending data to database");

        try {
            connectionFactory = InitialContext.doLookup(PathConstants.INCOMING_DATA_CONNECTION_FACTORY);
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
