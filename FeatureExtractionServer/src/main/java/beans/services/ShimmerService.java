package beans.services;

import com.google.gson.Gson;
import model.EyeTrackerMessage;
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
public class ShimmerService extends DeviceService{
    private static final Logger logger = Logger.getLogger(ShimmerService.class.getName());

    private Gson gson = new Gson();
    private String data;

    private ShimmerMessage shimmerMessage;
    @Inject
    @JMSConnectionFactory("jms/remoteCF")
    private JMSContext context;

    @Resource(lookup = "java:jboss/exported/jms/queue/shimmerRaw")
    private Queue queue;

    private String AVG_GSR_PPG = "http://142.93.109.50:5000/api/shimmer/avg";


    public void processMessage(String message){
//      Send first the rawdata to Database Server
        sendRawDataToDB(message);


//      feature Extraction: Average GSR and PPG
        shimmerMessage = getProcessedData(message,AVG_GSR_PPG);
        sendProcessedDataToDB(shimmerMessage);

    }

    public void sendRawDataToDB(String message){
        logger.info("send raw data to database");
        context.createProducer().send(queue,message);

    }

    public void sendProcessedDataToDB(ShimmerMessage message) {
        String msg = gson.toJson(message, ShimmerMessage.class);
        context.createProducer().send(queue, msg);

    }

    public ShimmerMessage getProcessedData(String message,String url) {

//        String url= "http://142.93.109.50:5000/api/eyetracker/substitution";
        String json = postToFlask(message, url);
        return gson.fromJson(json, ShimmerMessage.class);

    }


}
