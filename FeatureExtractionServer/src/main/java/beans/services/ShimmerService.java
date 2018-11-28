package beans.services;

import api.JNDIPaths;
import com.google.gson.Gson;
import model.EyeTrackerMessage;
import model.ShimmerMessage;
import org.jboss.logging.Logger;
import reply.ReplyManager;

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

    private String msg;
    private ReplyManager replyManager = ReplyManager.getInstance();

    private String address = "207.154.211.58"; // local server ip address
    private String port = "5000";              // port of the python web client

//    @Inject
//    @JMSConnectionFactory("jms/remoteCF")
//    private JMSContext context;

//    @Resource(lookup = JNDIPaths.SHIMMER_QUEUE)
    private String queue = JNDIPaths.SHIMMER_QUEUE;



    private String AVG_GSR_PPG = "http://"+ address + ":" + port +"/shimmer/normalize";


    public void processMessage(String message){
//      Clear the list if in the case of list not empty
        replyManager.clearList();
        replyManager.clearCount();
//      Send first the rawdata to Database Server
        sendDataToDB(message,queue);



//      feature Extraction: Normalization of GSR and PPG
        msg = postToFlask(message,AVG_GSR_PPG);
        if(msg != null){
            sendDataToDB(msg,queue);

        }

    }




}
