package databaseClient;

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
