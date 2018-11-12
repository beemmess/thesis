package client;

import client.domain.EyeTracker;
import client.repository.EyetrackerRepository;
import client.repository.KeyspaceRepository;
import com.datastax.driver.core.Session;
import org.jboss.logging.Logger;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class CassandraClient {


    private static final Logger logger = Logger.getLogger(CassandraClient.class);
    public static final String keyspace = "data";
    public static final CassandraConnector connector = new CassandraConnector();
//    Address of the server
    private static final String address = "104.248.35.208";


    public CassandraClient() {
        //Connect to cassandra using the default IP address and port 9042
        connector.connector(address, 9042);
        Session session = connector.getSession();


        //Create a new keyspace
        KeyspaceRepository keyspaceRepository = new KeyspaceRepository(session);
        keyspaceRepository.createKeyspace(keyspace, "SimpleStrategy", 1);
        keyspaceRepository.useKeyspace(keyspace);

        //Create the eyetracker Table if does not exist

        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        //TODO: take out droptable when devolopement is finished
//        eyetrackerRepository.dropTable();

        eyetrackerRepository.createTable();



    }
    public Boolean CassandraInsertRawValues(EyeTracker eyeTracker){
        Session session = connector.getSession();
        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        Boolean response = eyetrackerRepository.insertRawValues(eyeTracker);
//        eyetrackerRepository.getValues();
//        logger.info(response);
        return response;

    }

    public void CassandraInsertPreProcessedData(EyeTracker eyeTracker){
        Session session = connector.getSession();
        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        eyetrackerRepository.insertPreProcessedValues(eyeTracker);

    }

    public void CassandraInsertAvgPupilData(EyeTracker eyeTracker){
        Session session = connector.getSession();
        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        eyetrackerRepository.insertAvgPupilValues(eyeTracker);

    }

//    public String getDataFromUserId(String userId){
//        Session session = connector.getSession();
//        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
////        KeyspaceRepository keyspaceRepository = new KeyspaceRepository(session);
////        keyspaceRepository.useKeyspace(keyspace);
//
//        String resp = eyetrackerRepository.getValues(userId);
//        return resp;
//    }


    public void CassandraClose(){
        connector.close();
    }
}