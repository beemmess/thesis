package databaseClient;

import beans.PathConstants;
import databaseClient.repository.DeviceRepository;
import databaseClient.repository.KeyspaceRepository;
import com.datastax.driver.core.Session;
import org.jboss.logging.Logger;


/**
 * A Client that interacts with the Cassandra database, that contains conneciton information such as the
 * port and ip address of the docker container that contains the Cassandra database.
 * Initiating the constructor will create the keyspace "data" in the database
 */
public class CassandraClient {


    private static final Logger logger = Logger.getLogger(CassandraClient.class);
    public static final String keyspace = "data";
    public static final CassandraConnector connector = new CassandraConnector();
    private static final String address = PathConstants.DOCKER_LOCAL_NETWORK;


    /**
     * A client that initates a connection to the Cassandra database, as well as creates the
     * keyspace
     */
    public CassandraClient() {
        //Connect to cassandra using the default IP address and port 9042
        connector.connector(address, 9042);
        logger.info("Cassandra client initialised");
        Session session = connector.getSession();


        //Create a new keyspace if it does not exist
        KeyspaceRepository keyspaceRepository = new KeyspaceRepository(session);
        //keyspaceRepository.dropkeyspace(keyspace);
        keyspaceRepository.createKeyspace(keyspace, "SimpleStrategy", 1);
        keyspaceRepository.useKeyspace(keyspace);




    }
}
