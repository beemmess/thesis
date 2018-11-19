package client;

import client.domain.EyeTracker;
import client.domain.Shimmer;
import client.repository.ShimmerRepository;
import com.datastax.driver.core.Session;

public class ShimmerClient extends CassandraClient {


    private Session session = connector.getSession();

    public Boolean shimmerInsertRawValues(Shimmer shimmer){
//        Session session = connector.getSession();
        ShimmerRepository shimmerRepository = new ShimmerRepository(session);
        return shimmerRepository.insertRawValues(shimmer);

    }


    public Boolean shimmerInsertNormalizedValues(Shimmer shimmer){
        ShimmerRepository shimmerRepository = new ShimmerRepository(session);
        return shimmerRepository.insertNormalizedData(shimmer);


    }


}
