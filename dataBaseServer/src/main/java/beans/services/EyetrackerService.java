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

import com.sun.org.apache.xpath.internal.operations.Bool;
import model.EyeTrackerMessage;
import model.ReplyMessage;
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
    private ReplyMessage replyMessage = new ReplyMessage();
    private Boolean response;

    private String ERROR_RESPONSE = "Something went wrong, data was not saved to database";
    private String DATA_SAVED = "Data saved to database";

    public String saveDataToDB(String message) {

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
            return createJsonStringResponse(ERROR_RESPONSE,"InvalidData", false);
        }



    }


    public String saveRawData(EyeTrackerMessage eyeTrackerMessage) {

        try (BufferedReader br = new BufferedReader(new StringReader(eyeTrackerMessage.getData()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
//                EyeTracker eyeTracker = new EyeTracker(eyeTrackerMessage.getId(), Double.parseDouble(values[0]), values[1],values[2],values[3],values[4],values[5],values[6]);
                EyeTracker eyeTracker = new EyeTracker(eyeTrackerMessage.getId(), Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]), Double.parseDouble(values[3]), Double.parseDouble(values[4]), Double.parseDouble(values[5]), Double.parseDouble(values[6]));

                response = eyetrackerClient.EyetrackerInsertRawValues(eyeTracker);
                if(!response){
                    return createJsonStringResponse(ERROR_RESPONSE,eyeTrackerMessage.getType(), false);
                }
            }
            return createJsonStringResponse(DATA_SAVED,eyeTrackerMessage.getType(), true);

        } catch(IOException e) {
            logger.warn("error in Save raw Data " + e.toString());
            return createJsonStringResponse(ERROR_RESPONSE,eyeTrackerMessage.getType(), false);
        }

    }



    public String saveSubstitutionData(EyeTrackerMessage eyeTrackerMessage){

        try(BufferedReader br = new BufferedReader(new StringReader(eyeTrackerMessage.getData()))){
            String line;
            while((line = br.readLine()) != null ){
                String[] values = line.split(",");
//                EyeTracker eyeTracker = new EyeTracker(eyeTrackerMessage.getId(), Double.parseDouble(values[0]), values[1],values[2],values[3],values[4],values[5],values[6]);
                EyeTracker eyeTracker = new EyeTracker(eyeTrackerMessage.getId(), Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]), Double.parseDouble(values[3]), Double.parseDouble(values[4]), Double.parseDouble(values[5]), Double.parseDouble(values[6]));

                response = eyetrackerClient.EyetrackerInsertSubstitutionData(eyeTracker);
                if(!response){
                    return createJsonStringResponse(ERROR_RESPONSE,eyeTrackerMessage.getType(), false);
                }
            }
            return createJsonStringResponse(DATA_SAVED,eyeTrackerMessage.getType(), true);

        } catch (IOException e) {
            logger.warn("error in Save substituion Data " + e.toString());
            return createJsonStringResponse(ERROR_RESPONSE,eyeTrackerMessage.getType(), false);
        }
    }

    public String saveAvgPupilData(EyeTrackerMessage eyeTrackerMessage){

        try(BufferedReader br = new BufferedReader(new StringReader(eyeTrackerMessage.getData()))){
            String line;
            while((line = br.readLine()) != null ){
                String[] values = line.split(",");
                EyeTracker eyeTracker = new EyeTracker(eyeTrackerMessage.getId(), Double.parseDouble(values[0]), Double.parseDouble(values[1]));
                response = eyetrackerClient.EyetrackerInsertAvgPupilData(eyeTracker);
                if(!response){
                    return createJsonStringResponse(ERROR_RESPONSE,eyeTrackerMessage.getType(), false);
                }
            }
            return createJsonStringResponse(DATA_SAVED,eyeTrackerMessage.getType(), true);

        } catch (IOException e) {
            logger.warn("error in Save average Data " + e.toString());
            return createJsonStringResponse(ERROR_RESPONSE,eyeTrackerMessage.getType(), false);
        }
    }

    public String saveInterpolateData(EyeTrackerMessage eyeTrackerMessage){

        try(BufferedReader br = new BufferedReader(new StringReader(eyeTrackerMessage.getData()))){
            String line;
            while((line = br.readLine()) != null ){
                String[] values = line.split(",");
                EyeTracker eyeTracker = new EyeTracker(eyeTrackerMessage.getId(), Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]), Double.parseDouble(values[3]), Double.parseDouble(values[4]), Double.parseDouble(values[5]), Double.parseDouble(values[6]));
                response = eyetrackerClient.EyetrackerInsertInterpolateData(eyeTracker);
                if(!response){
                    return createJsonStringResponse(ERROR_RESPONSE,eyeTrackerMessage.getType(), false);
                }
            }
            return createJsonStringResponse(DATA_SAVED,eyeTrackerMessage.getType(), true);

        } catch (IOException e) {
            logger.warn("error in Save interpolate Data " + e.toString());
            return createJsonStringResponse(ERROR_RESPONSE,eyeTrackerMessage.getType(), false);
        }
    }


    public String createJsonStringResponse(String message, String data, Boolean success){
        replyMessage.setReplyMessage(message);
        replyMessage.setData(data);
        replyMessage.setSucess(success);
        return gson.toJson(replyMessage);
    }


}
