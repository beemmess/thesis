package client;

import client.domain.EyeTracker;
import client.repository.EyetrackerRepository;
import com.datastax.driver.core.Session;

public class EyetrackerClient extends CassandraClient {

    private Session session = connector.getSession();

    public Boolean EyetrackerInsertRawValues(EyeTracker eyeTracker){
//        Session session = connector.getSession();
        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        Boolean response = eyetrackerRepository.insertRawValues(eyeTracker);
//        eyetrackerRepository.getValues();
//        logger.info(response);
        return response;

    }

    public void EyetrackerInsertPreProcessedData(EyeTracker eyeTracker){
//        Session session = connector.getSession();
        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        eyetrackerRepository.insertPreProcessedValues(eyeTracker);

    }

    public void EyetrackerInsertAvgPupilData(EyeTracker eyeTracker){
//        Session session = connector.getSession();
        EyetrackerRepository eyetrackerRepository = new EyetrackerRepository(session);
        eyetrackerRepository.insertAvgPupilValues(eyeTracker);

    }
}
