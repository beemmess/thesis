package jms;



import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import client.domain.EyeTracker;
import client.CassandraClient;

@Named
@ApplicationScoped
public class Eyetracker {


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
