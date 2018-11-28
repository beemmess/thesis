package beans.services;

import api.JNDIPaths;
import org.jboss.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import reply.ReplyManager;


@Named
@ApplicationScoped
public class EyetrackerService extends DeviceService{

    private static final Logger logger = Logger.getLogger(EyetrackerService.class.getName());
    private ReplyManager replyManager = ReplyManager.getInstance();

    private String queue = JNDIPaths.EYETRACKER_QUEUE;

    private String msg;
    private String address = JNDIPaths.LOCAL_SERVER_IP;     // Local server ip address
    private String port = JNDIPaths.PYTHON_WEB_CLIENT_PORT; // port of the python web client

    private final String PRE_PROCESS_AND_SUBSTITION =   "http://" + address + ":" + port + "/eyetracker/substitution";
    private final String AVG_PUPIL =                    "http://" + address + ":" + port + ":5000/eyetracker/avgPupil";
    private final String AVG_PUPIL_PER_TASK =           "http://" + address + ":" + port + ":5000/eyetracker/avgPupil/perTask";
    private final String INTERPOLATE =                  "http://" + address + ":" + port + ":5000/eyetracker/interpolate";

    public void processMessage(String message) {
//      Send the rawdata to Database Server
        replyManager.clearList();
        replyManager.clearCount();
        replyManager.setCount(5);

        sendDataToDB(message, queue);

//      preprocess data: Gazepoint/pupil substitution link: https://arxiv.org/pdf/1703.09468.pdf
        msg = postToFlask(message,PRE_PROCESS_AND_SUBSTITION);
        sendDataToDB(msg, queue);
//
//      feature Extraction: Average pupil diameter
        msg = postToFlask(message,AVG_PUPIL);
        sendDataToDB(msg, queue);
//
//      feature Extraction: Average pupil diameter per task
        msg = postToFlask(message,AVG_PUPIL_PER_TASK);
        sendDataToDB(msg, queue);

//      preprocess data: interpolate https://arxiv.org/pdf/1703.09468.pdf
        msg = postToFlask(message,INTERPOLATE);
        sendDataToDB(msg, queue);

    }


}