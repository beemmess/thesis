package databaseClient.repository;

import com.datastax.driver.core.Session;
import org.jboss.logging.Logger;


/**
 * A super class that interacts with the database by sending queries to it
 */
public class CassandraRepository {
    private static final Logger logger = Logger.getLogger(CassandraRepository.class.getName());


    public final Session session;

    /**
     * A constructior that takes in the session of the Cassandra datbase to connect to it
     * @param session
     */
    public CassandraRepository(Session session){this.session = session;}


    /**
     * A method that executes all queries to the Database
     * @param query
     * @return Boolean, true if query was successful, false if it unsuccessful
     */
    public Boolean executeQuery(String query){
        try {
            session.execute(query);
            return true;
        }catch (Exception e){
            logger.info("false executeQuery: " + query);
            logger.error(e.toString());
            return false;
        }

    }
}
