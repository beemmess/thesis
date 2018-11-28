package beans.services;

import api.JNDIPaths;
import org.jboss.logging.Logger;
import reply.ReplyManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;


@Named
@ApplicationScoped
public class ShimmerService extends DeviceService{
    private static final Logger logger = Logger.getLogger(ShimmerService.class.getName());

    private String msg;
    private ReplyManager replyManager = ReplyManager.getInstance();

    private String address = JNDIPaths.LOCAL_SERVER_IP;     // local server ip address
    private String port = JNDIPaths.PYTHON_WEB_CLIENT_PORT; // port of the python web client

    private String queue = JNDIPaths.SHIMMER_QUEUE;

    private String AVG_GSR_PPG = "http://"+ address + ":" + port +"/shimmer/normalize";


    public void processMessage(String message){
//      Clear the list if in the case of list not empty
        replyManager.clearList();
        replyManager.clearCount();
        replyManager.setCount(2);
//      Send first the rawdata to Database Server
        sendDataToDB(message,queue);



//      feature Extraction: Normalization of GSR and PPG
        msg = postToFlask(message,AVG_GSR_PPG);
        sendDataToDB(msg,queue);


    }




}
