package databaseClient.repository;

import com.datastax.driver.core.Session;
import org.jboss.logging.Logger;

/**
 * A sub-Class that generates table statements and insert into statements
 */
public class DeviceRepository extends CassandraRepository {

    private static final Logger logger = Logger.getLogger(DeviceRepository.class.getName());


    /**
     * A constructor that takes in the session for the super class: CassandraRepository
     * @param session
     */
    public DeviceRepository(Session session) {
        super(session);
    }


    /**
     * A method that creates the table statement that will be insert into the database
     * @param device
     * @param type
     * @param attributes
     */
    public void createTable(String device, String type, String attributes){
        String query = createTableStatement(device, type, attributes);
        executeQuery(query);

    }

    /**
     * A helper method that creates the 'create table' statement
     * @param device
     * @param type
     * @param attributes
     * @return create table statement string
     */
    public String createTableStatement(String device, String type, String attributes) {
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
        return sb.toString();
    }


    /**
     * A method that creates the insert into statement that will be insert data into the database
     * @param type
     * @param device
     * @param attributes
     * @param dataid
     * @param line
     * @return Boolean if the statement was succesful or not
     */
    public Boolean insertValues(String type, String device, String attributes, String dataid, String line) {
        String query = createInsertStatement(type, device, attributes, dataid, line);
        return executeQuery(query);

    }

    /**
     * Helper method that generates the insert data statement
     * @param type
     * @param device
     * @param attributes
     * @param dataid
     * @param line
     * @return insert into statement
     */
    public String createInsertStatement(String type, String device, String attributes, String dataid, String line) {
        String[] values = line.split(",");
        int i = 0;
        int valuesLength = values.length;

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(device);
        sb.append("_");
        sb.append(type.toLowerCase());
        sb.append(" (dataid");
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

        return sb.toString();
    }

}



