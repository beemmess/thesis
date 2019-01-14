package databaseClient;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import org.jboss.logging.Logger;
/**
 * @author Bjarki
 * Based on tutorial:
 * https://github.com/nklkarthi/java-tutorials/blob/master/java-cassandra/src/main/java/com/baeldung/cassandra/java/client/CassandraConnector.java
 *
 */



public class CassandraConnector {
    private static final Logger logger = Logger.getLogger(CassandraConnector.class.getName());


    private Session session;
    private Cluster cluster;


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

    public Session getSession() {
        return this.session;
    }

    public void close() {
        session.close();
        cluster.close();
    }

}
