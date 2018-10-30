package jms.services;


import client.CassandraClient;
import client.domain.EyeTracker;
import jms.MdbEyetracker;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.jboss.logging.Logger;


@Named
@ApplicationScoped
public class EyetrackerService {
    private static final Logger logger = Logger.getLogger(EyetrackerService.class.getName());


    private String message;
    private String line;
    private String[] values;

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
//        try(BufferedReader br = new BufferedReader(new StringReader(message))){
//            br.readLine();
//            /*give the first line a 'null' as the first line consists of string
//            * e.g. timestamp, leftx, lefty .... etc*/
//            line = null;
//            while((line = br.readLine()) != null ){
//                values = line.split(",");
//                EyeTracker eyeTracker = new EyeTracker(Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]), Double.parseDouble(values[3]), Double.parseDouble(values[4]));
//                cassandraClient.CassandraInsertValues(eyeTracker);
//
//            }

        /*give the first line a 'null' as the first line consists of string
        * e.g. timestamp, leftx, lefty .... etc*/
        EyeTracker eyeTracker = new EyeTracker(message);
        cassandraClient.CassandraInsertValuesString(eyeTracker);
        cassandraClient.CassandraClose();
    }
}
