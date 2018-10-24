package client;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bjarki
 * Based on tutorial:
 * https://github.com/nklkarthi/java-tutorials/blob/master/java-cassandra/src/main/java/com/baeldung/cassandra/java/client/CassandraConnector.java
 *
 */



public class CassandraConnector {
    private static final Logger LOG = LoggerFactory.getLogger(CassandraConnector.class);

    private Session session;

    private Cluster cluster;


    public void connector(final String address, final Integer port){

        Cluster.Builder b = Cluster.builder().addContactPoint(address);


        if(port != null){
            b.withPort(port);
        }


        cluster = b.build();

        Metadata metadata = cluster.getMetadata();
        LOG.info("Cluser:" + metadata.getClusterName());


//        OPTIONAL???????
        for (Host host : metadata.getAllHosts()) {
            LOG.info("Datacenter: " + host.getDatacenter() + " Host: " + host.getAddress() + " Rack: " + host.getRack());
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
