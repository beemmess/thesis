package databaseClient;

import databaseClient.repository.DeviceRepository;
import com.datastax.driver.core.Session;
import org.jboss.logging.Logger;

/**
 * A subclass inserts the incoming data to the database as well as create database
 * with the incoming information about the data
 * This class uses the session that is generated in the superclass CassandraClient
 */
public class DeviceClient extends CassandraClient {
    private static final Logger logger = Logger.getLogger(DeviceClient.class.getName());


    private Session session = connector.getSession();
    DeviceRepository deviceRepository = new DeviceRepository(session);

    /**
     * A method that creates the table in the database
     * @param device
     * @param type
     * @param attributes
     */
    public void createTable(String device, String type, String attributes) {

        deviceRepository.createTable(device, type, attributes);

    }

    /**
     * A method that insert data into the table
     * @param type
     * @param device
     * @param attributes
     * @param dataid
     * @param line
     * @return
     */
    public Boolean insertData(String type, String device, String attributes, String dataid, String line){

        return deviceRepository.insertValues(type,device,attributes,dataid,line);

    }

}
