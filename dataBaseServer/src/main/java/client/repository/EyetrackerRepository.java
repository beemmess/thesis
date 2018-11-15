package client.repository;

import client.domain.EyeTracker;
import com.datastax.driver.core.Session;
import org.jboss.logging.Logger;

public class EyetrackerRepository extends CassandraRepository {

    private static final Logger logger = Logger.getLogger(EyetrackerRepository.class.getName());

    private static final String EYETRACKER_RAW = "eyetracker_raw";
    private static final String EYETRACKER_PRE_PROCESSED ="eyetracker_preprocessed";
    private static final String EYETRACKER_AVG_PUPIL = "eyetracker_avg_pupil";

    public EyetrackerRepository(Session session) {
        super(session);
    }


//    private Session session;

//    public EyetrackerRepository(Session session){
//        this.session = session;
//    }


    /*
    * Create the eyetracker tables
    * */

    public void createTable(){

        final String query = "CREATE TABLE IF NOT EXISTS " + EYETRACKER_RAW + "(timestamp double, dataid text, leftx text, lefty text, rightx text, righty text, pupilleft text, pupilright text, PRIMARY KEY ((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);";
        executeQuery(query);

        final String query2 = "CREATE TABLE IF NOT EXISTS " + EYETRACKER_PRE_PROCESSED + "(timestamp double, dataid text, leftx double, lefty double, rightx double, righty double, pupilleft double, pupilright double, PRIMARY KEY ((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);";
        executeQuery(query2);

        final String query3 = "CREATE TABLE IF NOT EXISTS " + EYETRACKER_AVG_PUPIL + "(dataid text PRIMARY KEY, pupilleft double, pupilright double);";
        executeQuery(query3);

    }

    public void dropTable() {
        final String query = "DROP TABLE IF EXISTS " + EYETRACKER_RAW + ";";
        executeQuery(query);

        final String query2 = "DROP TABLE IF EXISTS " + EYETRACKER_PRE_PROCESSED + ";";
        executeQuery(query2);

        final String query3 = "DROP TABLE IF EXISTS " + EYETRACKER_AVG_PUPIL + ";";
        executeQuery(query3);


    }

    /*
    * Insert values to table eyetracker
    * */

    public Boolean insertRawValues(EyeTracker eyeTracker) {

        final String query = "INSERT INTO " + EYETRACKER_RAW + "(timestamp, dataid, leftx, lefty, rightx, righty, pupilleft, pupilright) " + "VALUES ("+ eyeTracker.getTimestamp() + ", '" + eyeTracker.getId() + "', '"  + eyeTracker.getLeftxRaw() + "', '" + eyeTracker.getLeftyRaw() + "', '" + eyeTracker.getRightxRaw() + "', '" + eyeTracker.getRightyRaw()  + "', '" + eyeTracker.getPupilLRaw()  + "', '"+ eyeTracker.getPupilRRaw() + "');";
        Boolean resp = executeQuery(query);
//        logger.info(resp+ " " + query);
        return true;

    }
    public void insertPreProcessedValues(EyeTracker eyeTracker) {
        final String query = "INSERT INTO " + EYETRACKER_PRE_PROCESSED + "(timestamp, dataid, leftx, lefty, rightx, righty, pupilleft, pupilright) " + "VALUES ("+ eyeTracker.getTimestamp() + ", '" + eyeTracker.getId() + "', "  + eyeTracker.getLeftx() + ", " + eyeTracker.getLefty() + ", " + eyeTracker.getRightx() + ", " + eyeTracker.getRighty()  + ", " + eyeTracker.getPupilL()  + ", "+ eyeTracker.getPupilR() + ");";
        Boolean resp = executeQuery(query);
        logger.info(resp+ " " + query);

    }

    public Boolean insertAvgPupilValues(EyeTracker eyeTracker) {

        final String query = "INSERT INTO " + EYETRACKER_AVG_PUPIL + "(dataid, pupilleft, pupilright) " + "VALUES ('" +eyeTracker.getId() + "', " + eyeTracker.getPupilL()  + ", "+ eyeTracker.getPupilR() + ");";
        Boolean resp = executeQuery(query);
        logger.info(resp+ " " + query);
        return true;

    }


//    public Boolean executeQuery(String query){
//        try {
//            session.execute(query);
//            return true;
//        }catch (Exception e){
//            logger.info("false executeQuery: " + query);
//            return false;
//        }

    }



//    public String getValues(String id){
//        final String query = "SELECT * FROM " + EYETRACKER_RAW + " WHERE id = '"+ id +"' ALLOW FILTERING;";
//        ResultSet resultSet = session.execute(query);
//        return resultSet.all().toString();
//    }




