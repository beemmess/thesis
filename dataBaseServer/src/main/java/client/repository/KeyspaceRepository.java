package client.repository;

import com.datastax.driver.core.Session;


public class KeyspaceRepository extends CassandraRepository {

    public KeyspaceRepository(Session session) {
        super(session);
    }

    public void createKeyspace(String keyspaceName, String strategy, int replicas) {

        final String query = "CREATE KEYSPACE IF NOT EXISTS " + keyspaceName + " WITH replication = {" + "'class':'" + strategy + "','replication_factor':" + replicas + "};";

        executeQuery(query);
    }

    public void useKeyspace(String keyspace) {
        final String query = "USE " + keyspace;
        executeQuery(query);
    }

    public void dropkeyspace(String keyspaceName) {

        final String query = "DROP KEYSPACE " + keyspaceName;

       executeQuery(query);
    }
}