package beans.services;


import api.JNDIPaths;
import client.EyetrackerClient;
import com.google.gson.Gson;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

import model.EyeTrackerMessage;
import org.jboss.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import client.domain.EyeTracker;


@Named
@ApplicationScoped
public class EyetrackerService {
    private static final Logger logger = Logger.getLogger(EyetrackerService.class.getName());


//    @Inject
//    @JMSConnectionFactory("jms/remoteCF")
//    JMSContext jmsContext;
//
//    @Resource(lookup = JNDIPaths.REPLY_QUEUE)
//    Queue queue;

    private Gson gson = new Gson();
    private EyetrackerClient eyetrackerClient = new EyetrackerClient();
    private String replymsg;

    public String saveDataToDB(String message) {
        logger.info("before send message");
//        jmsContext.createProducer().send(queue,"this is a reply");
        logger.info("After send message");
        EyeTrackerMessage eyeTrackerMessage = gson.fromJson(message, EyeTrackerMessage.class);
        String type = eyeTrackerMessage.getType();
        if (type.equals("raw")) {
            logger.info(type);
            return saveRawData(eyeTrackerMessage);
        }
        else if (type.equals("substitution")) {
            logger.info(type);
            return saveSubstitutionData(eyeTrackerMessage);
        }
        else if (type.equals("avgPupil")) {
            logger.info(type);
            return saveAvgPupilData(eyeTrackerMessage);
        }
        else if (type.equals("interpolate")) {
            logger.info(type);
            return saveInterpolateData(eyeTrackerMessage);
        } else {
            logger.info("type not found: "+type);
            return "type not found";
        }



    }


    public String saveRawData(EyeTrackerMessage eyeTrackerMessage) {

        try (BufferedReader br = new BufferedReader(new StringReader(eyeTrackerMessage.getData()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                EyeTracker eyeTracker = new EyeTracker(eyeTrackerMessage.getId(), Double.parseDouble(values[0]), values[1],values[2],values[3],values[4],values[5],values[6]);
                eyetrackerClient.EyetrackerInsertRawValues(eyeTracker);
            }
            return "raw saved in database";

        } catch(IOException e) {
            e.printStackTrace();
            return "raw NOT saved in database";
        }

    }



    public String saveSubstitutionData(EyeTrackerMessage eyeTrackerMessage){

        try(BufferedReader br = new BufferedReader(new StringReader(eyeTrackerMessage.getData()))){
            String line;
            while((line = br.readLine()) != null ){
                String[] values = line.split(",");
                EyeTracker eyeTracker = new EyeTracker(eyeTrackerMessage.getId(), Double.parseDouble(values[0]), values[1],values[2],values[3],values[4],values[5],values[6]);
                eyetrackerClient.EyetrackerInsertSubstitutionData(eyeTracker);

            }
            return "substitution saved in database";

        } catch (IOException e) {
            e.printStackTrace();
            return "substitution NOT saved in database";
        }
    }

    public String saveAvgPupilData(EyeTrackerMessage eyeTrackerMessage){

        try(BufferedReader br = new BufferedReader(new StringReader(eyeTrackerMessage.getData()))){
            String line;
            while((line = br.readLine()) != null ){
                String[] values = line.split(",");
                EyeTracker eyeTracker = new EyeTracker(eyeTrackerMessage.getId(), Double.parseDouble(values[0]), Double.parseDouble(values[1]));
                eyetrackerClient.EyetrackerInsertAvgPupilData(eyeTracker);

            }
            return "avgPupil saved in database";

        } catch (IOException e) {
            e.printStackTrace();
            return "avgPupil NOT saved in database";
        }
    }

    public String saveInterpolateData(EyeTrackerMessage eyeTrackerMessage){

        try(BufferedReader br = new BufferedReader(new StringReader(eyeTrackerMessage.getData()))){
            String line;
            while((line = br.readLine()) != null ){
                String[] values = line.split(",");
                EyeTracker eyeTracker = new EyeTracker(eyeTrackerMessage.getId(), Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]), Double.parseDouble(values[3]), Double.parseDouble(values[4]), Double.parseDouble(values[5]), Double.parseDouble(values[6]));
                eyetrackerClient.EyetrackerInsertInterpolateData(eyeTracker);

            }
            return "interpolate saved in database";

        } catch (IOException e) {
            e.printStackTrace();
            return "interpolate NOT saved in database";
        }
    }



}