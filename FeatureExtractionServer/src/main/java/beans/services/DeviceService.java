package beans.services;

import api.JNDIPaths;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.jms.*;
import java.io.IOException;

public abstract class DeviceService {
    private static final Logger logger = Logger.getLogger(DeviceService.class.getName());

    @Resource(lookup = JNDIPaths.REPLY_QUEUE)
    Queue replyqueue;

    public String postToFlask(String message,String url){

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(message);

            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            httpClient.execute(request);
            HttpResponse result = httpClient.execute(request);

            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            return json;
        } catch (IOException ex) {
            logger.info(ex);
            return null;
        }


    }

    public void sendDataToDB(String message, JMSContext context, Queue queue){
        logger.info("send raw data to database");
        context.createProducer().send(queue,message);

//        TextMessage textMessage = (TextMessage) context.createConsumer(replyqueue).receive();
//        try {
//            logger.info(textMessage.getText());
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }


    }


}
