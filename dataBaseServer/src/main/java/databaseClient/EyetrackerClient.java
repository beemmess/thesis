package databaseClient;

import databaseClient.domain.EyeTracker;
import databaseClient.repository.EyetrackerRepository;
import com.datastax.driver.core.Session;
import org.jboss.logging.Logger;

import java.util.Arrays;

public class EyetrackerClient extends CassandraClient {
    private static final Logger logger = Logger.getLogger(EyetrackerClient.class.getName());


    private Session session = connector.getSession();
    EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);

    public Boolean EyetrackerInsertRawValues(EyeTracker eyeTracker){
//        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        return eyetrackerRepository.insertRawValues(eyeTracker);

    }

    public Boolean EyetrackerInsertSubstitutionData(EyeTracker eyeTracker){
//        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        return eyetrackerRepository.insertSubstitutionValues(eyeTracker);

    }

    public Boolean EyetrackerInsertAvgPupilData(EyeTracker eyeTracker){
//        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        return eyetrackerRepository.insertAvgPupilValues(eyeTracker);

    }

    public Boolean EyetrackerInsertAvgPupilDataPerTask(EyeTracker eyeTracker){
//        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        return eyetrackerRepository.insertAvgPupilPerTaskValues(eyeTracker);

    }

    public Boolean EyetrackerInsertInterpolateData(EyeTracker eyeTracker) {
//        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        return eyetrackerRepository.insertInterpolateValues(eyeTracker);
    }


    public void createTable(String device, String type, String features) {
        String[] featureList = features.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(device.toLowerCase());
        sb.append("_");
        sb.append(type.toLowerCase());
        sb.append(" (dataid text");
        for(String feature : featureList){
            if(feature.equals("task")){
                sb.append(", task text");
            }
            else{
                sb.append(", ");
                sb.append(feature.toLowerCase());
                sb.append(" double");
            }

        }
        sb.append(", ");
        sb.append("PRIMARY KEY");
        if(features.contains("timestamp")){
            sb.append("((dataid), timestamp)) ");
            sb.append("WITH CLUSTERING ORDER BY (timestamp ASC)");
        }
        else if(!features.contains("timestamp") && features.contains("task")) {
            sb.append("((dataid), task))");
        }
        else{
            sb.append(" (dataid))");
        }

        sb.append(";");
        String query = sb.toString();
//        logger.info(query);
        eyetrackerRepository.createTable(query);

    }


    public Boolean insertData(String type, String device, String features, String dataid, String line){

        return eyetrackerRepository.insertValues(type,device,features,dataid,line);

    }

}

// "CREATE TABLE IF NOT EXISTS " + EYETRACKER_AVG_PUPIL + "(dataid text PRIMARY KEY, pupilleft double, pupilright double);";
//CREATE TABLE IF NOT EXISTS eyetracker_avgPupil (dataid text, avgpupill double, avgpupilr double, PRIMARY KEYdataid);

// "CREATE TABLE IF NOT EXISTS " + EYETRACKER_RAW + "(timestamp double, dataid text, leftx double, lefty double, rightx double, righty double, pupilleft double, pupilright double, task text, PRIMARY KEY ((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);";
// CREATE TABLE IF NOT EXISTS eyetracker_raw (dataid text, timestamp double, leftx double, lefty double, rightx double, righty double, pupill double, pupilr double, task text, PRIMARY KEY((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);

// "CREATE TABLE IF NOT EXISTS " + EYETRACKER_SUSTITUTION + "(timestamp double, dataid text, leftx double, lefty double, rightx double, righty double, pupilleft double, pupilright double, task text, PRIMARY KEY ((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);";
//      CREATE TABLE IF NOT EXISTS eyetracker_substitution (dataid text, timestamp double, leftx double, lefty double, rightx double, righty double, pupill double, pupilr double, task text, PRIMARY KEY((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);

// "CREATE TABLE IF NOT EXISTS " + EYETRACKER_INTERPOLATE + "(timestamp double, dataid text, leftx double, lefty double, rightx double, righty double, pupilleft double, pupilright double, task text, PRIMARY KEY ((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);";
//CREATE TABLE IF NOT EXISTS eyetracker_interpolate (dataid text, timestamp double, leftx double, lefty double, rightx double, righty double, pupill double, pupilr double, task text, PRIMARY KEY((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);

// "CREATE TABLE IF NOT EXISTS " + EYETRACKER_AVG_PUPIL_PER_TASK + "(dataid text, pupilleft double, pupilright double, task text,PRIMARY KEY ((dataid), task));";
//    CREATE TABLE IF NOT EXISTS eyetracker_avgPupilTasks (dataid text, avgpupill double, avgpupilr double, task text, PRIMARY KEY((dataid), task));