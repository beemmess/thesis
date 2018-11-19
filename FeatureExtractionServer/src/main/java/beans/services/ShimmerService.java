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


    @Inject
    @JMSConnectionFactory("jms/remoteCF")
    private JMSContext context;

    @Resource(lookup = JNDIPaths.SHIMMER_QUEUE)
    private Queue queue;



    private String AVG_GSR_PPG = "http://142.93.109.50:5000/api/shimmer/normalize";


    public void processMessage(String message){
//      Send first the rawdata to Database Server
        sendDataToDB(message,context,queue);
        replyManager.setCount(1);



//      feature Extraction: Average GSR and PPG
        msg = postToFlask(message,AVG_GSR_PPG);
        if(msg != null){
            sendDataToDB(msg,context,queue);
            replyManager.setCount(2);

        }

    }




}
