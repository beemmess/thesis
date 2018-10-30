package jms.services;

import model.EyeTrackerMessage;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import com.google.gson.Gson;

import java.io.*;
import java.net.*;


//import database.EyeTrackerDB;

@Named
@ApplicationScoped
public class EyeTrackerService {
    private static final Logger logger = Logger.getLogger(EyeTrackerService.class.getName());

    private Gson gson = new Gson();
    private String data;
//    @Inject
//    @JMSConnectionFactory("jms/remoteCF")
//    private JMSContext context;
//
//    @Resource(lookup = "java:jboss/exported/jms/queue/eyetrackerRaw")
//    private Queue queue;

    public void sendRawDataToDB(String message){
//        logger.info("send raw data to database");
//        EyeTrackerMessage eyeTrackerMessage = gson.fromJson(message, EyeTrackerMessage.class);
//        data = eyeTrackerMessage.getData();
//        context.createProducer().send(queue,data);

    }

    public void sendPostRawDataToDB(String message) throws IOException {
        logger.info("sendPostRawDataToDB send message");
// https://stackoverflow.com/questions/22816335/java-httprequest-json-response-handling


            String url = "http://104.248.35.208:9090/dataBaseServer/api/eyetrackerRaw";

            try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                HttpPost request = new HttpPost(url);
                StringEntity params = new StringEntity(message);
                request.addHeader("content-type", "application/json");
                request.setEntity(params);
                httpClient.execute(request);

//                HttpResponse result = httpClient.execute(request);
//                String json = EntityUtils.toString(result.getEntity(), "UTF-8");
//
//                Gson gson = new Gson();
//                model.EyeTrackerMessage respuesta = gson.fromJson(json, model.EyeTrackerMessage.class);
//
//                logger.info(respuesta.getData());


            } catch (IOException ex) {
                logger.info(ex);
            }
        }


}