package databaseClient.repository;

import databaseClient.domain.EyeTracker;
import com.datastax.driver.core.Session;
import org.jboss.logging.Logger;

public class DeviceRepository extends CassandraRepository {

    private static final Logger logger = Logger.getLogger(DeviceRepository.class.getName());

    public DeviceRepository(Session session) {
        super(session);
    }



    public void createTable(String device, String type, String attributes){
        String[] attributeList = attributes.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(device.toLowerCase());
        sb.append("_");
        sb.append(type.toLowerCase());
        sb.append(" (dataid text");
        for(String attribute : attributeList){
            if(attribute.equals("task")){
                sb.append(", task text");
            }else{
                sb.append(", ");
                sb.append(attribute.toLowerCase());
                sb.append(" double");
            }
        }
        sb.append(", ");
        sb.append("PRIMARY KEY");
        if(attributes.contains("timestamp")){
            sb.append("((dataid), timestamp)) ");
            sb.append("WITH CLUSTERING ORDER BY (timestamp ASC)");
        }
        else if(!attributes.contains("timestamp") && attributes.contains("task")) {
            sb.append("((dataid), task))");
        }
        else{
            sb.append(" (dataid))");
        }
        sb.append(";");
        String query = sb.toString();
        executeQuery(query);

    }



    public Boolean insertValues(String type, String device, String attributes, String dataid, String line) {
//        logger.info(attributes);
        String[] values = line.split(",");
        int i = 0;
        int valuesLength = values.length;

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(device);
        sb.append("_");
        sb.append(type.toLowerCase());
        sb.append(" (dataid ");
        sb.append(", ");
        sb.append(attributes);
        sb.append(") VALUES (");
        sb.append("'");
        sb.append(dataid);
        sb.append("'");
        for(String value : values) {
            sb.append(", ");
            if(i++ == valuesLength-1 && attributes.contains("task")){
                sb.append("'");
                sb.append(value);
                sb.append("'");
            }
            else {
                sb.append(value);
            }
        }
        sb.append(");");

        String query = sb.toString();
//        logger.info(query);
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



