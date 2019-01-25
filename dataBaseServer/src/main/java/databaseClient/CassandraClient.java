package databaseClient;

import beans.PathConstants;
import databaseClient.repository.DeviceRepository;
import databaseClient.repository.KeyspaceRepository;
import com.datastax.driver.core.Session;
import org.jboss.logging.Logger;


public class CassandraClient {


    private static final Logger logger = Logger.getLogger(CassandraClient.class);
    public static final String keyspace = "data";
    public static final CassandraConnector connector = new CassandraConnector();
    private static final String address = PathConstants.DOCKER_LOCAL_NETWORK;


    public CassandraClient() {
        //Connect to cassandra using the default IP address and port 9042
        connector.connector(address, 9042);
        logger.info("Cassandra client initialised");
        Session session = connector.getSession();


        //Create a new keyspace if it does not exist
        KeyspaceRepository keyspaceRepository = new KeyspaceRepository(session);
        keyspaceRepository.dropkeyspace(keyspace);
        keyspaceRepository.createKeyspace(keyspace, "SimpleStrategy", 1);
        keyspaceRepository.useKeyspace(keyspace);




    }




    public void CassandraClose(){
        connector.close();
    }
}