package databaseClient;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import org.jboss.logging.Logger;


/**
 * A class that handles the connection to the Cassandra database
 */
public class CassandraConnector {
    private static final Logger logger = Logger.getLogger(CassandraConnector.class.getName());


    private Session session;
    private Cluster cluster;


    /**
     * A connector method to connect to the Cassandra Database
     * @param address
     * @param port
     */
    public void connector(final String address, final Integer port){

        Cluster.Builder builder = Cluster.builder().addContactPoint(address);


        if(port != null){
            builder.withPort(port);
        }

        cluster = builder.build();

        Metadata metadata = cluster.getMetadata();
        logger.info("Cluster:" + metadata.getClusterName());

        for (Host host : metadata.getAllHosts()) {
            logger.info("Datacenter: " + host.getDatacenter() + " Host: " + host.getAddress() + " Rack: " + host.getRack());
        }

        session = cluster.connect();

    }


    /**
     * A method that returns the session that whitholds the connection session to the Cassandra Database
     * @return Session
     */
    public Session getSession() {
        return this.session;
    }

    public void close() {
        session.close();
        cluster.close();
    }

}
