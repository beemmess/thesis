package databaseClient;

import beans.PathConstants;
import databaseClient.repository.EyetrackerRepository;
import databaseClient.repository.KeyspaceRepository;
import databaseClient.repository.ShimmerRepository;
import com.datastax.driver.core.Session;
import org.jboss.logging.Logger;


public class CassandraClient {


    private static final Logger logger = Logger.getLogger(CassandraClient.class);
    public static final String keyspace = "data";
    public static final CassandraConnector connector = new CassandraConnector();
//    Address of the server
    private static final String address = PathConstants.LOCAL_SERVER_IP;


    public CassandraClient() {
        //Connect to cassandra using the default IP address and port 9042
        connector.connector(address, 9042);
        logger.info("Cassandra client initilized");
        Session session = connector.getSession();


        //Create a new keyspace
        KeyspaceRepository keyspaceRepository = new KeyspaceRepository(session);
        keyspaceRepository.dropkeyspace(keyspace);
        keyspaceRepository.createKeyspace(keyspace, "SimpleStrategy", 1);
        keyspaceRepository.useKeyspace(keyspace);

        //Create the eyetracker Table if does not exist
        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        ShimmerRepository shimmerRepository = new ShimmerRepository(session);
//        CassandraRepository cassandraRepository = new CassandraRepository(session);
        //TODO: take out droptable when devolopement is finished
        eyetrackerRepository.dropTable();
        shimmerRepository.dropTable();



//        eyetrackerRepository.createTable();
        shimmerRepository.createTable();



    }




    public void CassandraClose(){
        connector.close();
    }
}