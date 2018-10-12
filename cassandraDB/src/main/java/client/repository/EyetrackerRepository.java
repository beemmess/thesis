package client.repository;

import com.datastax.driver.core.Session;
import client.domain.EyeTracker;

public class EyetrackerRepository {

    private static final String TABLE_NAME = "eyetracker";

    private Session session;

    public EyetrackerRepository(Session session){
        this.session = session;
    }


    /*
    * Create the eyetracker table
    * */

    public void createTable(){
//        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append("(timestamp double PRIMARY KEY, leftx double, lefty double, rightx double, righty double);");
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append("(id int PRIMARY KEY,values text);");

        final String query = sb.toString();
        session.execute(query);
    }

    /*
    * Insert values to table eyetracker
    * */

//    public void insertValues(EyeTracker eyeTracker) {
////        StringBuilder sb = new StringBuilder("INSERT INTO ").append(TABLE_NAME).append("(timestamp, leftx, lefty, rightx, righty) ").append("VALUES (").append(eyeTracker.getTimestamp()).append(", '").append(eyeTracker.getLeftx()).append(", '").append(eyeTracker.getLefty()).append("', '").append(eyeTracker.getRightx()).append("', '").append(eyeTracker.getRighty()).append("');");
//        StringBuilder sb = new StringBuilder("INSERT INTO ").append(TABLE_NAME).append("(timestamp, leftx, lefty, rightx, righty) ").append("VALUES (").append(eyeTracker.getTimestamp()).append(", ").append(eyeTracker.getLefty()).append(", ").append(eyeTracker.getLefty()).append(", ").append(eyeTracker.getRightx()).append(", ").append(eyeTracker.getRighty()).append(");");
//
//        final String query = sb.toString();
//        session.execute(query);
//    }

    public void insertValuesString(EyeTracker eyeTracker) {
//        StringBuilder sb = new StringBuilder("INSERT INTO ").append(TABLE_NAME).append("(timestamp, leftx, lefty, rightx, righty) ").append("VALUES (").append(eyeTracker.getTimestamp()).append(", '").append(eyeTracker.getLeftx()).append(", '").append(eyeTracker.getLefty()).append("', '").append(eyeTracker.getRightx()).append("', '").append(eyeTracker.getRighty()).append("');");
        StringBuilder sb = new StringBuilder("INSERT INTO ").append(TABLE_NAME).append("(id, values) ").append("VALUES (1, '").append(eyeTracker.getValues()).append("');");

        final String query = sb.toString();
        session.execute(query);
    }

}
