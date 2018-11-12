package beans.services;


import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ShimmerService {

    private static final Logger logger = Logger.getLogger(ShimmerService.class.getName());


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
        logger.info("setValues in shimmerService ");

//        CassandraClient cassandraClient = new CassandraClient();
//        cassandraClient.CassandraClient();
////        try(BufferedReader br = new BufferedReader(new StringReader(message))){
////            br.readLine();
////            /*give the first line a 'null' as the first line consists of string
////            * e.g. timestamp, leftx, lefty .... etc*/
////            line = null;
////            while((line = br.readLine()) != null ){
////                values = line.split(",");
////                EyeTrackerMessage eyeTracker = new EyeTrackerMessage(Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]), Double.parseDouble(values[3]), Double.parseDouble(values[4]));
////                cassandraClient.CassandraInsertRawValues(eyeTracker);
////
////            }
//
//        /*give the first line a 'null' as the first line consists of string
//        * e.g. timestamp, leftx, lefty .... etc*/
//        EyeTrackerMessage eyeTracker = new EyeTrackerMessage(message);
//        cassandraClient.CassandraInsertValuesString(eyeTracker);
//        cassandraClient.CassandraClose();
    }
}
