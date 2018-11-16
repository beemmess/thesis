package client;

import client.domain.EyeTracker;
import client.repository.EyetrackerRepository;
import com.datastax.driver.core.Session;

public class EyetrackerClient extends CassandraClient {

    private Session session = connector.getSession();

    public Boolean EyetrackerInsertRawValues(EyeTracker eyeTracker){
        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        Boolean response = eyetrackerRepository.insertRawValues(eyeTracker);
        return response;

    }

    public void EyetrackerInsertSubstitutionData(EyeTracker eyeTracker){
        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        eyetrackerRepository.insertSubstitutionValues(eyeTracker);

    }

    public void EyetrackerInsertAvgPupilData(EyeTracker eyeTracker){
        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        eyetrackerRepository.insertAvgPupilValues(eyeTracker);

    }

    public void EyetrackerInsertInterpolateData(EyeTracker eyeTracker) {
        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        eyetrackerRepository.insertInterpolateValues(eyeTracker);
    }
}
