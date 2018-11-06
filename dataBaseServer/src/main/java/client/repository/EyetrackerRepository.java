package client.repository;

import client.domain.EyeTracker;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import org.jboss.logging.Logger;

public class EyetrackerRepository {

    private static final Logger logger = Logger.getLogger(EyetrackerRepository.class.getName());

    private static final String TABLE_NAME = "eyetrackerraw";

    private Session session;

    public EyetrackerRepository(Session session){
        this.session = session;
    }


    /*
    * Create the eyetracker table
    * */

    public void createTable(){

        final String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(timestamp double, leftx text, lefty text, rightx text, righty text, userid text, PRIMARY KEY ((userid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);";
        session.execute(query);
    }

    public void dropTable() {
        final String query = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        session.execute(query);
    }

    /*
    * Insert values to table eyetracker
    * */

    public Boolean insertValues(EyeTracker eyeTracker) {

        final String query = "INSERT INTO " + TABLE_NAME + "(timestamp, leftx, lefty, rightx, righty, userid) " + "VALUES ("+ eyeTracker.getTimestamp() + ", '" + eyeTracker.getLeftx() + "', '" + eyeTracker.getLefty() + "', '" + eyeTracker.getRightx() + "', '" + eyeTracker.getRighty()  + "', '" + eyeTracker.getUserId() + "');";
        logger.info(query);
        try {
            session.execute(query);
            return true;
        }catch (Exception e){
            logger.info("false");
            return false;
        }

    }

    public String getValues(String userId){
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE userid = '"+ userId +"' ALLOW FILTERING;";
        ResultSet resultSet = session.execute(query);
        return resultSet.all().toString();
    }



}
