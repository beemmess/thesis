package client.repository;

import com.datastax.driver.core.Session;

/**
 * Repository to handle the Cassandra schema.
 * based on tutorial:
 * https://github.com/nklkarthi/java-tutorials/blob/master/java-cassandra/src/main/java/com/baeldung/cassandra/java/client/repository/KeyspaceRepository.java
 */
public class KeyspaceRepository {
    private Session session;

    public KeyspaceRepository(Session session) {
        this.session = session;
    }

    /**
     * Method used to create any keyspace - schema.
     *
     * @param schemaName the name of the schema.
     * @param replicatioonStrategy the replication strategy.
     * @param numberOfReplicas the number of replicas.
     *
     */
    public void createKeyspace(String keyspaceName, String replicatioonStrategy, int numberOfReplicas) {

        final String query = "CREATE KEYSPACE IF NOT EXISTS " + keyspaceName + " WITH replication = {" + "'class':'" + replicatioonStrategy + "','replication_factor':" + numberOfReplicas + "};";

        session.execute(query);
    }

    public void useKeyspace(String keyspace) {
        session.execute("USE " + keyspace);
    }

    /**
     * Method used to delete the specified schema.
     * It results in the immediate, irreversable removal of the keyspace, including all tables and data contained in the keyspace.
     *
     * @param schemaName the name of the keyspace to delete.
     */
    public void deleteKeyspace(String keyspaceName) {

        final String query = "DROP KEYSPACE " + keyspaceName;

        session.execute(query);
    }
}