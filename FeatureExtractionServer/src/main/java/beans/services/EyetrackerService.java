package beans.services;

import api.PathConstants;
import com.google.gson.Gson;
import model.EyeTrackerMessage;
import org.jboss.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import reply.ReplyManager;


@Named
@ApplicationScoped
public class EyetrackerService extends DeviceService{

    private static final Logger logger = Logger.getLogger(EyetrackerService.class.getName());
    private ReplyManager replyManager = ReplyManager.getInstance();

    private String queue = PathConstants.EYETRACKER_QUEUE;
    private Gson gson = new Gson();

    private String msg;
    private String address = PathConstants.LOCAL_SERVER_IP;     // Local server ip address
    private String port = PathConstants.PYTHON_WEB_CLIENT_PORT; // port of the python web client

//    private final String PRE_PROCESS_AND_SUBSTITUTION =  "http://" + address + ":" + port + "/eyetracker/substitution";
//    private final String AVG_PUPIL                    =  "http://" + address + ":" + port + "/eyetracker/avgPupil";
//    private final String AVG_PUPIL_PER_TASK           =  "http://" + address + ":" + port + "/eyetracker/avgPupil/perTask";
//    private final String INTERPOLATE                  =  "http://" + address + ":" + port + "/eyetracker/interpolate";

//    public void processMessage(String message) {
//        replyManager.clearList();
//        replyManager.clearCount();
//        replyManager.setCount(5);
// //     Send the rawdata to Database Server
//        sendDataToDB(message, queue);
//
////      preprocess data: Gazepoint/pupil substitution link: https://arxiv.org/pdf/1703.09468.pdf
//        msg = postToFlask(message, PRE_PROCESS_AND_SUBSTITUTION);
//        sendDataToDB(msg, queue);
////
////      feature Extraction: Average pupil diameter
//        msg = postToFlask(message,AVG_PUPIL);
//        sendDataToDB(msg, queue);
////
////      feature Extraction: Average pupil diameter per task
//        msg = postToFlask(message,AVG_PUPIL_PER_TASK);
//        sendDataToDB(msg, queue);
//
////      preprocess data: interpolate https://arxiv.org/pdf/1703.09468.pdf
//        msg = postToFlask(message,INTERPOLATE);
//        sendDataToDB(msg, queue);
//
//    }



    public void processMessage(String message){
        EyeTrackerMessage eyeTrackerMessage = gson.fromJson(message, EyeTrackerMessage.class);
        String[] apiUrls = eyeTrackerMessage.getApiUrl().split(",");
        replyManager.clearList();
        replyManager.clearCount();
        replyManager.setCount(apiUrls.length+1);

        sendDataToDB(message, queue);

        for(String apiUrl : apiUrls){
            String url = "http://" + address + ":" + port + apiUrl;
            msg = postToFlask(message, url);
            sendDataToDB(msg,queue);
        }


    }


}