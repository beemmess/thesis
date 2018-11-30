package databaseClient.repository;

import databaseClient.domain.Shimmer;
import com.datastax.driver.core.Session;

public class ShimmerRepository extends CassandraRepository {

    private static final String SHIMMER_RAW = "shimmer_raw";
    private static final String SHIMMER_NORMALIZED = "shimmer_normalized";



    public ShimmerRepository(Session session) {
        super(session);
    }

    public void createTable() {

        final String query = "CREATE TABLE IF NOT EXISTS " + SHIMMER_RAW + "(timestamp double, dataid text, gsr double, ppg double, task text, PRIMARY KEY ((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);";
        executeQuery(query);
        final String query2 = "CREATE TABLE IF NOT EXISTS " + SHIMMER_NORMALIZED + "(timestamp double, dataid text, gsr double, ppg double, task text, PRIMARY KEY ((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);";
        executeQuery(query2);

    }

    public void dropTable() {
        final String query = "DROP TABLE IF EXISTS " + SHIMMER_RAW + ";";
        executeQuery(query);
        final String query1 = "DROP TABLE IF EXISTS " + SHIMMER_NORMALIZED + ";";
        executeQuery(query1);

    }




    public Boolean insertRawValues(Shimmer shimmer) {

        final String query = "INSERT INTO " + SHIMMER_RAW + "(timestamp, dataid, gsr, ppg, task) " + "VALUES ("+ shimmer.getTimestamp() + ", '" + shimmer.getId() + "', "  + shimmer.getGsr() + ", " + shimmer.getPpg() + ",'" +shimmer.getTask()+"');";
        return executeQuery(query);

    }

    public Boolean insertNormalizedData(Shimmer shimmer) {

        final String query = "INSERT INTO " + SHIMMER_NORMALIZED + "(timestamp, dataid, gsr, ppg, task) " + "VALUES ("+ shimmer.getTimestamp() + ", '" + shimmer.getId() + "', "  + shimmer.getGsr() + ", " + shimmer.getPpg() + ",'" +shimmer.getTask()+"');";
        return executeQuery(query);
    }
}
