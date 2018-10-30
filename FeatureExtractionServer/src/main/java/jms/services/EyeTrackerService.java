package jms.services;

import model.EyeTrackerMessage;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import com.google.gson.Gson;


//import database.EyeTrackerDB;

@Named
@ApplicationScoped
public class EyeTrackerService {
    private static final Logger logger = Logger.getLogger(EyeTrackerService.class.getName());

    private Gson gson = new Gson();
    private String data;
    @Inject
    @JMSConnectionFactory("jms/remoteCF")
    private JMSContext context;

    @Resource(lookup = "java:jboss/exported/jms/queue/eyetrackerRaw")
    private Queue queue;

    public void sendRawDataToDB(String message){
        logger.info("send raw data to database");
        EyeTrackerMessage eyeTrackerMessage = gson.fromJson(message, EyeTrackerMessage.class);
        data = eyeTrackerMessage.getData();
        context.createProducer().send(queue,data);

    }

}