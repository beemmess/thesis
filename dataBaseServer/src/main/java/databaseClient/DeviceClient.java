package databaseClient;

import databaseClient.domain.EyeTracker;
import databaseClient.repository.DeviceRepository;
import com.datastax.driver.core.Session;
import org.jboss.logging.Logger;

public class DeviceClient extends CassandraClient {
    private static final Logger logger = Logger.getLogger(DeviceClient.class.getName());


    private Session session = connector.getSession();
    DeviceRepository deviceRepository = new DeviceRepository(session);


    public void createTable(String device, String type, String attributes) {

        deviceRepository.createTable(device, type, attributes);

    }


    public Boolean insertData(String type, String device, String attributes, String dataid, String line){

        return deviceRepository.insertValues(type,device,attributes,dataid,line);

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