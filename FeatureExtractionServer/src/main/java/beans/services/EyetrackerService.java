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

import java.io.*;


@Named
@ApplicationScoped
public class EyetrackerService extends DeviceService{

    private static final Logger logger = Logger.getLogger(EyetrackerService.class.getName());

    @Inject
    @JMSConnectionFactory("jms/remoteCF")
    private JMSContext context;

    @Resource(lookup = JNDIPaths.EYETRACKER_QUEUE)
    private Queue queue;

    private String msg;

    private final String FILLNA = "http://142.93.109.50:5000/api/eyetracker/fillnan";
    private final String PRE_PROCESS_AND_SUBSTITION = "http://142.93.109.50:5000/api/eyetracker/substitution";
    private final String AVG_PUPIL = "http://142.93.109.50:5000/api/eyetracker/avgPupil";
    private final String INTERPOLATE = "http://142.93.109.50:5000/api/eyetracker/interpolate";

    public void processMessage(String message) {
//      Send the rawdata to Database Server

        sendDataToDB(message, context, queue);

//      preprocess data: Gazepoint/pupil substitution link: https://arxiv.org/pdf/1703.09468.pdf
        msg = postToFlask(message,PRE_PROCESS_AND_SUBSTITION);
        if(msg != null) {
            sendDataToDB(msg, context, queue);
        }
//      feature Extraction: Average pupil diameter
        msg = postToFlask(message,AVG_PUPIL);
        if(msg !=null) {
            sendDataToDB(msg, context, queue);
        }

        msg = postToFlask(message,INTERPOLATE);
        if(msg !=null) {
            sendDataToDB(msg, context, queue);
        }

    }


}