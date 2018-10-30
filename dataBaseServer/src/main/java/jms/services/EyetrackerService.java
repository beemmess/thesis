package jms.services;


import client.CassandraClient;
import client.domain.EyeTracker;
import com.google.gson.Gson;
import jms.MdbEyetracker;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.jboss.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import model.EyeTrackerMessage;


@Named
@ApplicationScoped
public class EyetrackerService {
    private static final Logger logger = Logger.getLogger(EyetrackerService.class.getName());


    private String message;
    private Gson gson = new Gson();

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;

//        System.out.print(message);
//        setValues();
        setValues();

    }

    public void setValues(){
        CassandraClient cassandraClient = new CassandraClient();
        cassandraClient.CassandraClient();
        EyeTrackerMessage eyeTrackerMessage = gson.fromJson(message, EyeTrackerMessage.class);



        try(BufferedReader br = new BufferedReader(new StringReader(eyeTrackerMessage.getData()))){
            br.readLine();

            /*give the first line a 'null' as the first line consists of string
            * e.g. timestamp, leftx, lefty .... etc*/
//            line = null;


            String line;
            while((line = br.readLine()) != null ){
                logger.info(line);
                String[] values = line.split(",");
                logger.info(Double.parseDouble(values[0])+"======== "+values[0]);
                Double double0 = Double.parseDouble(values[0]);
                Double double1 = Double.parseDouble(values[1]);
                Double double2 = Double.parseDouble(values[2]);
                Double double3 = Double.parseDouble(values[3]);
                Double double4 = Double.parseDouble(values[4]);

//                EyeTracker eyeTracker = new EyeTracker(Double.(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]), Double.parseDouble(values[3]), Double.parseDouble(values[4]));
                EyeTracker eyeTracker = new EyeTracker(double0,double1,double2,double3,double4);
                cassandraClient.CassandraInsertValues(eyeTracker);

            }


    } catch (IOException e) {
            e.printStackTrace();
        }

//        /*give the first line a 'null' as the first line consists of string
//         * e.g. timestamp, leftx, lefty .... etc*/
//        EyeTracker eyeTracker = new EyeTracker(message);
//        cassandraClient.CassandraInsertValuesString(eyeTracker);
//        cassandraClient.CassandraClose();
    }


}
