package jms.services;

import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

//import database.EyeTrackerDB;

@Named
@ApplicationScoped
public class EyeTrackerService {
    private static final Logger logger = Logger.getLogger(EyeTrackerService.class.getName());

    private String message;
    private String line;
    private String[] values;
//    private EyeTrackerDB eyeTrackerDB = new EyeTrackerDB();


    public String getMessage() {
        return message;
    }

    @Inject
    @JMSConnectionFactory("jms/remoteCF")
    private JMSContext context;

    @Resource(lookup = "java:jboss/exported/jms/queue/ExampleQueue")
    private Queue queue;

    public void sendMessageToDB(String message){
        logger.info("DO I COME HERE IN sendMEssageToDB");

        context.createProducer().send(queue,message);

    }

    public void createEyeTrackerService(String msg) {
//        this.message = msg;
//        SaveRawToDatabase(msg);
//        eyeTrackerDB.SaveToDatabase(msg);
    }

    public void SaveRawToDatabase(String msg) {

    }

    public void setMessage(String message) {
        this.message = message;
//        logger.info(message);
        logger.info("Im now in setMessage in EyetrackerService");
//        setValues();

//        setValues();
//
    }

    public void setValues() {
//        CassandraClient cassandraClient = new CassandraClient();
//        cassandraClient.CassandraClient();
//        logger.info("setValues");
//        EyeTracker eyeTracker = new EyeTracker(message);
//        cassandraClient.CassandraInsertValuesString(eyeTracker);
//        cassandraClient.CassandraClose();
//        try (BufferedReader br = new BufferedReader(new StringReader(message))) {
//            br.readLine();
//            /*give the first line a 'null' as the first line consists of string
//             * e.g. timestamp, leftx, lefty .... etc*/
//            line = null;
//            while ((line = br.readLine()) != null) {
//                values = line.split(",");
////                EyeTracker eyeTracker = new EyeTracker(Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]), Double.parseDouble(values[3]), Double.parseDouble(values[4]));
////                cassandraClient.CassandraInsertValues(eyeTracker);
//
//            }
//
//            /*give the first line a 'null' as the first line consists of string
//             * e.g. timestamp, leftx, lefty .... etc*/
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}