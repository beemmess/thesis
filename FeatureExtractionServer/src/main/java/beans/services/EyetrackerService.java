package beans.services;

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

import java.io.*;


@Named
@ApplicationScoped
public class EyetrackerService {
    private static final Logger logger = Logger.getLogger(EyetrackerService.class.getName());

    private Gson gson = new Gson();
    @Inject
    @JMSConnectionFactory("jms/remoteCF")
    private JMSContext context;

    @Resource(lookup = "java:jboss/exported/jms/queue/eyetrackerRaw")
    private Queue queueRaw;
//
    @Resource(lookup = "java:jboss/exported/jms/queue/eyetrackerProcessed")
    private Queue queueProcessed;

    public Boolean processMessage(String message) {

            if(message != null) {
                sendRawDataToDB(message);
                EyeTrackerMessage eyeTrackerMessage = preProcessMessage(message);
                sendProcessedDataToDB(eyeTrackerMessage);
                return true;
            }

    return false;

    }

    public Boolean sendRawDataToDB(String message){
        if(message !=null){
            logger.info("send raw data to database");
            context.createProducer().send(queueRaw,message);
            return true;
        }else return false;


    }
    public void sendProcessedDataToDB(EyeTrackerMessage message) {
        if(message != null) {
            logger.info("send raw data to database");
            String msg = gson.toJson(message, EyeTrackerMessage.class);
            logger.info("json string " + msg);
            context.createProducer().send(queueProcessed, msg);
        }

    }
// Gazepoint/pupil substition link: https://arxiv.org/pdf/1703.09468.pdf
//
// page 5 in the article

    public Boolean test(){
        return true;
    }

    public EyeTrackerMessage preProcessMessage(String message) {
        EyeTrackerMessage eyeTrackerMessage = gson.fromJson(message, EyeTrackerMessage.class);

        if (message != null) {
//            EyeTrackerMessage eyeTrackerMessage = gson.fromJson(message, EyeTrackerMessage.class);

            String data = eyeTrackerMessage.getData();
            logger.info("processMessage");

            try (BufferedReader br = new BufferedReader(new StringReader(data))) {
                String line;
                String text = null;

//            values[0] = timestamp
//            values[1] = leftx
//            values[2] = lefty
//            values[3] = rightx
//            values[4] = righty
//            values[5] = pupil Left
//            values[6] = pupil Right
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
//              If Gazepoints values are missing, then all values are dropped.
//              OR Missing both of pupil data
                    if ((values[1].equals("nan") && values[3].equals("nan"))
                            || (values[2].equals("nan") && values[4].equals("nan"))
                            || (values[5].equals("nan") && values[6].equals("nan"))) {
                        continue;
                    }
                    if (values[1].equals("nan")) {
                        values[1] = values[3];
                    }
                    if (values[3].equals("nan")) {
                        values[3] = values[1];
                    }
                    if (values[2].equals("nan")) {
                        values[2] = values[4];
                    }
                    if (values[4].equals("nan")) {
                        values[4] = values[2];
                    }
//                Pupil substitution
                    if (values[5].equals("nan")) {
                        if (!values[6].equals("nan")) {
                            values[5] = values[6];
                        }
                    }
                    if (values[6].equals("nan")) {
                        values[6] = values[5];
                    }

                    text += values[0] + "," + values[1] + "," + values[2] + "," + values[3] + "," + values[4] + "," + values[5] + "," + values[6] + "\n";

                }
                logger.info(text);
                eyeTrackerMessage.setData(text);
                return eyeTrackerMessage;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return eyeTrackerMessage;
        }
        return eyeTrackerMessage;


    }

//    public void

}