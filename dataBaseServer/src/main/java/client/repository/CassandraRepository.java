package client.repository;

import client.CassandraClient;
import com.datastax.driver.core.Session;
import org.jboss.logging.Logger;

public class CassandraRepository {
    private static final Logger logger = Logger.getLogger(CassandraRepository.class.getName());


    public final Session session;

    public CassandraRepository(Session session){this.session = session;}



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
