package beans.services;


import client.EyetrackerClient;
import com.google.gson.Gson;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

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


    private Gson gson = new Gson();
//    private CassandraClient cassandraClient = new CassandraClient();
    private EyetrackerClient eyetrackerClient = new EyetrackerClient();

    public void saveDataToDB(String message){
//        cassandraClient.CassandraClient();
        EyeTrackerMessage eyeTrackerMessage = gson.fromJson(message, EyeTrackerMessage.class);
        String type = eyeTrackerMessage.getType();
        if(type.equals("raw")) {
            logger.info(type);
            saveRawData(eyeTrackerMessage);
        }
        if(type.equals("preprocessed")){
            logger.info(type);
            savePreProcessedData(eyeTrackerMessage);
        }
        if(type.equals("avgPupil")){
            logger.info(type);
            saveAvgPupilData(eyeTrackerMessage);
        }
        else{
            logger.info("type not found: ");
        }


    }


    public void saveRawData(EyeTrackerMessage eyeTrackerMessage) {


        try(BufferedReader br = new BufferedReader(new StringReader(eyeTrackerMessage.getData()))){
            String line;
            while((line = br.readLine()) != null ){
//                logger.info(line);
                String[] values = line.split(",");
                EyeTracker eyeTracker = new EyeTracker(eyeTrackerMessage.getId(), Double.parseDouble(values[0]), values[1], values[2], values[3], values[4], values[5], values[6]);
                eyetrackerClient.EyetrackerInsertRawValues(eyeTracker);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void savePreProcessedData(EyeTrackerMessage eyeTrackerMessage){

        try(BufferedReader br = new BufferedReader(new StringReader(eyeTrackerMessage.getData()))){
            String line;
            while((line = br.readLine()) != null ){
                logger.info(line);
                String[] values = line.split(",");
                EyeTracker eyeTracker = new EyeTracker(eyeTrackerMessage.getId(), Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]), Double.parseDouble(values[3]), Double.parseDouble(values[4]), Double.parseDouble(values[5]), Double.parseDouble(values[6]));
                eyetrackerClient.EyetrackerInsertPreProcessedData(eyeTracker);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAvgPupilData(EyeTrackerMessage eyeTrackerMessage){

        try(BufferedReader br = new BufferedReader(new StringReader(eyeTrackerMessage.getData()))){
            String line;
            while((line = br.readLine()) != null ){
                String[] values = line.split(",");
                EyeTracker eyeTracker = new EyeTracker(eyeTrackerMessage.getId(), Double.parseDouble(values[0]), Double.parseDouble(values[1]));
                eyetrackerClient.EyetrackerInsertAvgPupilData(eyeTracker);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
