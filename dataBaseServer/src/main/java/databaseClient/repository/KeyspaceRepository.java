package databaseClient.repository;

import com.datastax.driver.core.Session;


/**
 * This sub class takes care of the keyspace generation and using the keyspace.
 */
public class KeyspaceRepository extends CassandraRepository {

    /**
     * A constructor that takes in the session for the super class: CassandraRepository
     * @param session
     */
    public KeyspaceRepository(Session session) {
        super(session);
    }

    /**
     * This method handles the cretion of the keyspace and executes the query
     * @param keyspaceName
     * @param strategy
     * @param replicas
     */
    public void createKeyspace(String keyspaceName, String strategy, int replicas) {

        final String query = "CREATE KEYSPACE IF NOT EXISTS " + keyspaceName + " WITH replication = {" + "'class':'" + strategy + "','replication_factor':" + replicas + "};";

        executeQuery(query);
    }

    /**
     * This medhod sends a statement to the database telling to use the incoming keyspace string
     * @param keyspace
     */
    public void useKeyspace(String keyspace) {
        final String query = "USE " + keyspace;
        executeQuery(query);
    }

    /**
     * A method to drop the keyspace
     * @param keyspaceName
     */
    public void dropkeyspace(String keyspaceName) {

        final String query = "DROP KEYSPACE " + keyspaceName;

       executeQuery(query);
    }
}