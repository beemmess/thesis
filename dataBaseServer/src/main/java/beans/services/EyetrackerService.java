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
        setValues();

    }

    public void setValues(){
        CassandraClient cassandraClient = new CassandraClient();
        cassandraClient.CassandraClient();
        EyeTrackerMessage eyeTrackerMessage = gson.fromJson(message, EyeTrackerMessage.class);


        try(BufferedReader br = new BufferedReader(new StringReader(eyeTrackerMessage.getData()))){
            br.readLine();
            String line;
            while((line = br.readLine()) != null ){
                String[] values = line.split(",");

                client.domain.EyeTracker eyeTracker = new client.domain.EyeTracker(Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]), Double.parseDouble(values[3]), Double.parseDouble(values[4]));
                cassandraClient.CassandraInsertValues(eyeTracker);

            }


    } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
