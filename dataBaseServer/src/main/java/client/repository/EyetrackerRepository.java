package client.repository;

import client.domain.EyeTracker;
import com.datastax.driver.core.Session;

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

        //        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append("(id int PRIMARY KEY,values text);");

        final String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(timestamp double PRIMARY KEY, leftx double, lefty double, rightx double, righty double);";
        session.execute(query);
    }

    /*
    * Insert values to table eyetracker
    * */

    public Boolean insertValues(EyeTracker eyeTracker) {
//        StringBuilder sb = new StringBuilder("INSERT INTO ").append(TABLE_NAME).append("(timestamp, leftx, lefty, rightx, righty) ").append("VALUES (").append(eyeTracker.getTimestamp()).append(", '").append(eyeTracker.getLeftx()).append(", '").append(eyeTracker.getLefty()).append("', '").append(eyeTracker.getRightx()).append("', '").append(eyeTracker.getRighty()).append("');");
        //        StringBuilder sb = new StringBuilder("INSERT INTO ").append(TABLE_NAME).append("(timestamp, leftx, lefty, rightx, righty) ").append("VALUES (0.1234567891234").append(", ").append(eyeTracker.getLeftx()).append(", ").append(eyeTracker.getLefty()).append(", ").append(eyeTracker.getRightx()).append(", ").append(eyeTracker.getRighty()).append(");");

        final String query = "INSERT INTO " + TABLE_NAME + "(timestamp, leftx, lefty, rightx, righty) " + "VALUES (" + eyeTracker.getTimestamp() + ", " + eyeTracker.getLeftx() + ", " + eyeTracker.getLefty() + ", " + eyeTracker.getRightx() + ", " + eyeTracker.getRighty() + ");";
        try {
            session.execute(query);
            return true;
        }catch (Exception e){
            return false;
        }

    }

//    public void insertValuesString(EyeTrackerMessage eyeTracker) {
////        StringBuilder sb = new StringBuilder("INSERT INTO ").append(TABLE_NAME).append("(timestamp, leftx, lefty, rightx, righty) ").append("VALUES (").append(eyeTracker.getTimestamp()).append(", '").append(eyeTracker.getLeftx()).append(", '").append(eyeTracker.getLefty()).append("', '").append(eyeTracker.getRightx()).append("', '").append(eyeTracker.getRighty()).append("');");
//        StringBuilder sb = new StringBuilder("INSERT INTO ").append(TABLE_NAME).append("(id, values) ").append("VALUES (1, '").append(eyeTracker.getValues()).append("');");
//
//        final String query = sb.toString();
//        session.execute(query);
//    }

}
