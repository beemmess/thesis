package request;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class Request {

    private static final Logger logger = Logger.getLogger(Request.class.getName());

    public HttpResponse postToFlaskPythonServer(String url, String message) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(message);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            return httpClient.execute(request);

        } catch (
                IOException ex) {
            logger.info(ex.toString());
            return null;
        }
    }


}
