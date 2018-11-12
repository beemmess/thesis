package beans.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jboss.logging.Logger;

import java.io.IOException;

public abstract class DeviceService {
    private static final Logger logger = Logger.getLogger(DeviceService.class.getName());


    public String postToFlask(String message,String url){

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(message);

            logger.info("message" + message);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            httpClient.execute(request);
            HttpResponse result = httpClient.execute(request);
            logger.info(result.getEntity());
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

//            Gson gson = new Gson();
//            eyeTrackerMessage = gson.fromJson(json, model.EyeTrackerMessage.class);

            logger.info(json);
            return json;
        } catch (IOException ex) {
            logger.info(ex);
            message = null;
            return message;
        }


    }
}
