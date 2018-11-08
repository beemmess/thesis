package beans.services;


import client.CassandraClient;
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



    public void saveRawData(String message) {
        CassandraClient cassandraClient = new CassandraClient();
        cassandraClient.CassandraClient();
        EyeTrackerMessage eyeTrackerMessage = gson.fromJson(message, EyeTrackerMessage.class);

        try(BufferedReader br = new BufferedReader(new StringReader(eyeTrackerMessage.getData()))){
//            br.readLine();
            String line;
            while((line = br.readLine()) != null ){
                logger.info(line);
                String[] values = line.split(",");
                EyeTracker eyeTracker = new EyeTracker(eyeTrackerMessage.getUserId(), Double.parseDouble(values[0]), values[1], values[2], values[3], values[4], values[5], values[6]);
                cassandraClient.CassandraInsertValues(eyeTracker);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveProcessedData(String message){

        logger.info(message);
    }



}
