package jms.services;

import com.google.gson.Gson;
import model.ShimmerMessage;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Named
@ApplicationScoped
public class ShimmerService {
    private static final Logger logger = Logger.getLogger(ShimmerService.class.getName());

    private Gson gson = new Gson();
    private String data;
    @Inject
    @JMSConnectionFactory("jms/remoteCF")
    private JMSContext context;

    @Resource(lookup = "java:jboss/exported/jms/queue/shimmerRaw")
    private Queue queue;

    public void sendRawDataToDB(String message){
        logger.info("send raw data to database");
        logger.info("send raw data to database");
        ShimmerMessage shimmerMessage = gson.fromJson(message, ShimmerMessage.class);
        data = shimmerMessage.getData();

        context.createProducer().send(queue,data);

    }


}
