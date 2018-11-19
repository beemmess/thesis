package client;

import client.domain.EyeTracker;
import client.repository.EyetrackerRepository;
import com.datastax.driver.core.Session;

public class EyetrackerClient extends CassandraClient {

    private Session session = connector.getSession();

    public Boolean EyetrackerInsertRawValues(EyeTracker eyeTracker){
        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        return eyetrackerRepository.insertRawValues(eyeTracker);

    }

    public Boolean EyetrackerInsertSubstitutionData(EyeTracker eyeTracker){
        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        return eyetrackerRepository.insertSubstitutionValues(eyeTracker);

    }

    public Boolean EyetrackerInsertAvgPupilData(EyeTracker eyeTracker){
        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        return eyetrackerRepository.insertAvgPupilValues(eyeTracker);

    }

    public Boolean EyetrackerInsertInterpolateData(EyeTracker eyeTracker) {
        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        return eyetrackerRepository.insertInterpolateValues(eyeTracker);
    }
}
