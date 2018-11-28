package beans.services;

import api.JNDIPaths;
import model.EyeTrackerMessage;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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
import reply.ReplyManager;

import java.io.*;


@Named
@ApplicationScoped
public class EyetrackerService extends DeviceService{

    private static final Logger logger = Logger.getLogger(EyetrackerService.class.getName());

    private ReplyManager replyManager = ReplyManager.getInstance();

//    @Inject
//    @JMSConnectionFactory("jms/remoteCF")
//    private JMSContext context;

//    @Resource(lookup = JNDIPaths.EYETRACKER_QUEUE)
    private String queue = JNDIPaths.EYETRACKER_QUEUE;

    private String msg;
    private String address = "207.154.211.58";  // Local server ip address
    private String port = "5000";               // port of the python web client

    private final String PRE_PROCESS_AND_SUBSTITION =   "http://" + address + ":" + port + "/eyetracker/substitution";
    private final String AVG_PUPIL =                    "http://" + address + ":" + port + ":5000/eyetracker/avgPupil";
    private final String AVG_PUPIL_PER_TASK =           "http://" + address + ":" + port + ":5000/eyetracker/avgPupil/perTask";
    private final String INTERPOLATE =                  "http://" + address + ":" + port + ":5000/eyetracker/interpolate";

    public void processMessage(String message) {
//      Send the rawdata to Database Server
        replyManager.clearList();
        replyManager.clearCount();
        logger.info("is this the address" + address);

        sendDataToDB(message, queue);

//      preprocess data: Gazepoint/pupil substitution link: https://arxiv.org/pdf/1703.09468.pdf
        msg = postToFlask(message,PRE_PROCESS_AND_SUBSTITION);
        if(msg != null) {
            sendDataToDB(msg, queue);
        }
//      feature Extraction: Average pupil diameter
        msg = postToFlask(message,AVG_PUPIL);
        if(msg !=null) {
            sendDataToDB(msg, queue);
        }
//      feature Extraction: Average pupil diameter per task
        msg = postToFlask(message,AVG_PUPIL_PER_TASK);
        if(msg !=null) {
            sendDataToDB(msg, queue);
        }

//      preprocess data: interpolate https://arxiv.org/pdf/1703.09468.pdf
        msg = postToFlask(message,INTERPOLATE);
        if(msg !=null) {
            sendDataToDB(msg, queue);
        }

    }


}