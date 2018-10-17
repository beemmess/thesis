package client;

import client.domain.EyeTracker;
import client.repository.EyetrackerRepository;
import client.CassandraConnector;
import client.repository.KeyspaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Session;

public class CassandraClient {


//    private static final Logger LOG = LoggerFactory.getLogger(CassandraClient.class);
    public static final String keyspace = "data";
    public static final CassandraConnector connector = new CassandraConnector();
//    Address of the server
    private static final String address = "142.93.109.50";

    public void CassandraClient() {
        //Connect to cassandra using the default IP address and port 9042
        connector.connector(address, 9042);
        Session session = connector.getSession();

        //Create a new keyspace
        KeyspaceRepository sr = new KeyspaceRepository(session);
        sr.createKeyspace(keyspace, "SimpleStrategy", 1);
        sr.useKeyspace(keyspace);

        //Create the eyetracker Table
        EyetrackerRepository er = new EyetrackerRepository(session);
        er.createTable();


        //Insert a new entry
//        EyeTracker book = new EyeTracker(9876.1, 1.1, 2.1, 3.1, 4.1);
//        EyeTracker book1 = new EyeTracker(9876.2, 1234567.2, 1234567.2, 1234567.2, 2345.2);
//        EyeTracker book2 = new EyeTracker(9876.3, 123456.3, 1234567.3, 1234567.3, 12345.3);
//        er.insertValues(book);
//        br.insertValues(book1);
//        br.insertValues(book2);

//        br.selectAll().forEach(o -> LOG.info("Title in books: " + o.getTitle()));
//        br.selectAllBookByTitle().forEach(o -> LOG.info("Title in booksByTitle: " + o.getTitle()));

        //br.deletebookByTitle("CASSANDRA");
        //br.deleteTable("books");
        //br.deleteTable("booksByTitle");

        //Drop keyspace
        //sr.deleteKeyspace(keyspace);

        //Close connection to cassandra
//        connector.close();
    }
    public void CassandraInsertValues(EyeTracker eyeTracker){
        Session session = connector.getSession();
        EyetrackerRepository er = new EyetrackerRepository(session);
//        er.insertValues(eyeTracker);
    }

    public void CassandraInsertValuesString(EyeTracker eyeTracker){
        Session session = connector.getSession();
        EyetrackerRepository er = new EyetrackerRepository(session);
        er.insertValuesString(eyeTracker);
    }

    public void CassandraClose(){
        connector.close();
    }
}