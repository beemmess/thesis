package client.repository;

import client.domain.EyeTracker;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EyetrackerRepository extends CassandraRepository {

    private static final Logger logger = Logger.getLogger(EyetrackerRepository.class.getName());

    private static final String EYETRACKER_RAW = "eyetracker_raw";
    private static final String EYETRACKER_SUSTITUTION ="eyetracker_substitution";
    private static final String EYETRACKER_AVG_PUPIL = "eyetracker_avg_pupil";
    private static final String EYETRACKER_AVG_PUPIL_PER_TASK = "eyetracker_avg_pupil_per_task";
    private static final String EYETRACKER_INTERPOLATE = "eyetracker_interpolate";

    public EyetrackerRepository(Session session) {
        super(session);
    }



    public void createTable(){

        final String query = "CREATE TABLE IF NOT EXISTS " + EYETRACKER_RAW + "(timestamp double, dataid text, leftx double, lefty double, rightx double, righty double, pupilleft double, pupilright double, task text, PRIMARY KEY ((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);";
        executeQuery(query);

        final String query2 = "CREATE TABLE IF NOT EXISTS " + EYETRACKER_SUSTITUTION + "(timestamp double, dataid text, leftx double, lefty double, rightx double, righty double, pupilleft double, pupilright double, task text, PRIMARY KEY ((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);";
        executeQuery(query2);

        final String query3 = "CREATE TABLE IF NOT EXISTS " + EYETRACKER_AVG_PUPIL + "(dataid text PRIMARY KEY, pupilleft double, pupilright double);";
        executeQuery(query3);

        final String query4 = "CREATE TABLE IF NOT EXISTS " + EYETRACKER_INTERPOLATE + "(timestamp double, dataid text, leftx double, lefty double, rightx double, righty double, pupilleft double, pupilright double, task text, PRIMARY KEY ((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);";
        executeQuery(query4);

        final String query5 = "CREATE TABLE IF NOT EXISTS " + EYETRACKER_AVG_PUPIL_PER_TASK + "(dataid text, pupilleft double, pupilright double, task text,PRIMARY KEY ((dataid), task));";
        executeQuery(query5);


    }

    public void dropTable() {
        final String query = "DROP TABLE IF EXISTS " + EYETRACKER_RAW + ";";
        executeQuery(query);

        final String query2 = "DROP TABLE IF EXISTS " + EYETRACKER_SUSTITUTION + ";";
        executeQuery(query2);

        final String query3 = "DROP TABLE IF EXISTS " + EYETRACKER_AVG_PUPIL + ";";
        executeQuery(query3);

        final String query4 = "DROP TABLE IF EXISTS " + EYETRACKER_INTERPOLATE + ";";
        executeQuery(query4);

        final String query5 = "DROP TABLE IF EXISTS " + EYETRACKER_AVG_PUPIL_PER_TASK + ";";
        executeQuery(query5);


    }

    /*
    * Insert values to table eyetracker
    * */

    public Boolean insertRawValues(EyeTracker eyeTracker) {
        final String query = "INSERT INTO " + EYETRACKER_RAW + "(timestamp, dataid, leftx, lefty, rightx, righty, pupilleft, pupilright, task) " + "VALUES ("+ eyeTracker.getTimestamp() + ", '" + eyeTracker.getId() + "', "  + eyeTracker.getLeftx() + ", " + eyeTracker.getLefty() + ", " + eyeTracker.getRightx() + ", " + eyeTracker.getRighty()  + ", " + eyeTracker.getPupilL()  + ", "+ eyeTracker.getPupilR() + ", '"+ eyeTracker.getTask() +"');";

        return executeQuery(query);

    }
    public Boolean insertSubstitutionValues(EyeTracker eyeTracker) {
        final String query = "INSERT INTO " + EYETRACKER_SUSTITUTION + "(timestamp, dataid, leftx, lefty, rightx, righty, pupilleft, pupilright, task) " + "VALUES ("+ eyeTracker.getTimestamp() + ", '" + eyeTracker.getId() + "', "  + eyeTracker.getLeftx() + ", " + eyeTracker.getLefty() + ", " + eyeTracker.getRightx() + ", " + eyeTracker.getRighty()  + ", " + eyeTracker.getPupilL()  + ", "+ eyeTracker.getPupilR() + ", '"+ eyeTracker.getTask() +"');";

        return executeQuery(query);

    }

    public Boolean insertAvgPupilValues(EyeTracker eyeTracker) {

        final String query = "INSERT INTO " + EYETRACKER_AVG_PUPIL + "(dataid, pupilleft, pupilright) " + "VALUES ('" +eyeTracker.getId() + "', " + eyeTracker.getPupilL()  + ", "+ eyeTracker.getPupilR() + ");";
        return executeQuery(query);

    }


    public Boolean insertAvgPupilPerTaskValues(EyeTracker eyeTracker) {

        final String query = "INSERT INTO " + EYETRACKER_AVG_PUPIL_PER_TASK + "(dataid, pupilleft, pupilright,task) " + "VALUES ('" +eyeTracker.getId() + "', " + eyeTracker.getPupilL()  + ", "+ eyeTracker.getPupilR() + ", '" + eyeTracker.getTask() +"');";
        return executeQuery(query);

    }


    public Boolean insertInterpolateValues(EyeTracker eyeTracker) {
        final String query = "INSERT INTO " + EYETRACKER_INTERPOLATE + "(timestamp, dataid, leftx, lefty, rightx, righty, pupilleft, pupilright, task) " + "VALUES ("+ eyeTracker.getTimestamp() + ", '" + eyeTracker.getId() + "', "  + eyeTracker.getLeftx() + ", " + eyeTracker.getLefty() + ", " + eyeTracker.getRightx() + ", " + eyeTracker.getRighty()  + ", " + eyeTracker.getPupilL()  + ", "+ eyeTracker.getPupilR() + ", '"+ eyeTracker.getTask() +"');";
        return executeQuery(query);
    }




//    Dummy select query
//    public void selectAllDataById() {
//
//        final String query = "SELECT * FROM eyetracker_avg_pupil;";
//        ResultSet rs = session.execute(query);
//
//        List<EyeTracker> eyeTrackerList = new ArrayList<EyeTracker>();
//
//        for (Row r : rs) {
//            EyeTracker eyeTracker = new EyeTracker(r.getString("dataid"), r.getDouble("pupilleft"), r.getDouble("pupilright"));
//            eyeTrackerList.add(eyeTracker);
//        }
//        logger.info("SElect all eyetracker avg pupil" + eyeTrackerList.toString());
//    }
}



