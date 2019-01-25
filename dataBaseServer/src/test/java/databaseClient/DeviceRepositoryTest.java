package databaseClient;

import databaseClient.repository.DeviceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.modules.junit4.PowerMockRunner;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class DeviceRepositoryTest {

    @Mock
    private DeviceRepository deviceRepository;


    public DeviceRepositoryTest() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void createTableStatementEyetrackerRaw()  {
        String device = "eyetracker";
        String type = "raw";
        String attributes = "timestamp,leftx,lefty,rightx,righty,pupilL,pupilR,task";
        Mockito.doCallRealMethod().when(deviceRepository).createTableStatement(device,type,attributes);
        String statement = deviceRepository.createTableStatement(device,type,attributes);
        String correctStatement = "CREATE TABLE IF NOT EXISTS eyetracker_raw (dataid text, timestamp double, leftx double, lefty double, rightx double, righty double, pupill double, pupilr double, task text, PRIMARY KEY((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);";
        assertThat(statement, is(correctStatement));
    }

    @Test
    public void createTableStatementEyetrackerSubstitution()  {
        String device = "eyetracker";
        String type = "substitution";
        String attributes = "timestamp,leftx,lefty,rightx,righty,pupilL,pupilR,task";
        Mockito.doCallRealMethod().when(deviceRepository).createTableStatement(device,type,attributes);
        String statement = deviceRepository.createTableStatement(device,type,attributes);
        String correctStatement = "CREATE TABLE IF NOT EXISTS eyetracker_substitution (dataid text, timestamp double, leftx double, lefty double, rightx double, righty double, pupill double, pupilr double, task text, PRIMARY KEY((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);";
        assertThat(statement, is(correctStatement));
    }

    @Test
    public void createTableStatementEyetrackerInterpolation()  {
        String device = "eyetracker";
        String type = "interpolation";
        String attributes = "timestamp,leftx,lefty,rightx,righty,pupilL,pupilR,task";
        Mockito.doCallRealMethod().when(deviceRepository).createTableStatement(device,type,attributes);
        String statement = deviceRepository.createTableStatement(device,type,attributes);
        String correctStatement = "CREATE TABLE IF NOT EXISTS eyetracker_interpolation (dataid text, timestamp double, leftx double, lefty double, rightx double, righty double, pupill double, pupilr double, task text, PRIMARY KEY((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);";
        assertThat(statement, is(correctStatement));
    }

    @Test
    public void createTableStatementEyetrackerAvgPupil()  {
        String device = "eyetracker";
        String type = "avgpupil";
        String attributes = "avgpupill,avgpupilr";
        Mockito.doCallRealMethod().when(deviceRepository).createTableStatement(device,type,attributes);
        String statement = deviceRepository.createTableStatement(device,type,attributes);
        String correctStatement = "CREATE TABLE IF NOT EXISTS eyetracker_avgpupil (dataid text, avgpupill double, avgpupilr double, PRIMARY KEY (dataid));";
        assertThat(statement, is(correctStatement));
    }

    @Test
    public void createTableStatementEyetrackerAvgPupilEachTask()  {
        String device = "eyetracker";
        String type = "avgpupiltask";
        String attributes = "avgpupill,avgpupilr,task";
        Mockito.doCallRealMethod().when(deviceRepository).createTableStatement(device,type,attributes);
        String statement = deviceRepository.createTableStatement(device,type,attributes);
        String correctStatement = "CREATE TABLE IF NOT EXISTS eyetracker_avgpupiltask (dataid text, avgpupill double, avgpupilr double, task text, PRIMARY KEY((dataid), task));";
        assertThat(statement, is(correctStatement));
    }

    @Test
    public void createTableStatementShimmerRaw()  {
        String device = "shimmer";
        String type = "raw";
        String attributes = "timestamp,gsr,ppg,task";
        Mockito.doCallRealMethod().when(deviceRepository).createTableStatement(device,type,attributes);
        String statement = deviceRepository.createTableStatement(device,type,attributes);
        String correctStatement = "CREATE TABLE IF NOT EXISTS shimmer_raw (dataid text, timestamp double, gsr double, ppg double, task text, PRIMARY KEY((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);";
        assertThat(statement, is(correctStatement));
    }

    @Test
    public void createTableStatementShimmerNormalise()  {
        String device = "shimmer";
        String type = "normalise";
        String attributes = "timestamp,gsr,ppg,task";
        Mockito.doCallRealMethod().when(deviceRepository).createTableStatement(device,type,attributes);
        String statement = deviceRepository.createTableStatement(device,type,attributes);
        String correctStatement = "CREATE TABLE IF NOT EXISTS shimmer_normalise (dataid text, timestamp double, gsr double, ppg double, task text, PRIMARY KEY((dataid), timestamp)) WITH CLUSTERING ORDER BY (timestamp ASC);";
        assertThat(statement, is(correctStatement));
    }


    @Test
    public void createInsertStatementShimmerNormalise()  {
        String device = "shimmer";
        String type = "normalise";
        String attributes = "timestamp,gsr,ppg,task";
        String dataid = "unitTest";
        String line = "1,2,3,helloRaw";
        Mockito.doCallRealMethod().when(deviceRepository).createInsertStatement(type,device,attributes, dataid,line);
        String statement = deviceRepository.createInsertStatement(type,device,attributes, dataid,line);
        String correctStatement = "INSERT INTO shimmer_normalise (dataid, timestamp,gsr,ppg,task) VALUES ('unitTest', 1, 2, 3, 'helloRaw');";
        assertThat(statement, is(correctStatement));
    }

    @Test
    public void createInsertStatementShimmerRaw()  {
        String device = "shimmer";
        String type = "raw";
        String attributes = "timestamp,gsr,ppg,task";
        String dataid = "unitTest";
        String line = "3,2,1,helloNormalise";
        Mockito.doCallRealMethod().when(deviceRepository).createInsertStatement(type,device,attributes, dataid,line);
        String statement = deviceRepository.createInsertStatement(type,device,attributes, dataid,line);
        String correctStatement = "INSERT INTO shimmer_raw (dataid, timestamp,gsr,ppg,task) VALUES ('unitTest', 3, 2, 1, 'helloNormalise');";
        assertThat(statement, is(correctStatement));
    }


    @Test
    public void createInsertStatementEyetrackerRaw()  {
        String device = "eyetracker";
        String type = "raw";
        String attributes = "timestamp,leftx,lefty,rightx,righty,pupilL,pupilR,task";
        String dataid = "unitTest";
        String line = "1,2,3,4,5,6,7,hi";
        Mockito.doCallRealMethod().when(deviceRepository).createInsertStatement(type,device,attributes, dataid,line);
        String statement = deviceRepository.createInsertStatement(type,device,attributes, dataid,line);
        String correctStatement = "INSERT INTO eyetracker_raw (dataid, timestamp,leftx,lefty,rightx,righty,pupilL,pupilR,task) VALUES ('unitTest', 1, 2, 3, 4, 5, 6, 7, 'hi');";
        assertThat(statement, is(correctStatement));
    }

    @Test
    public void createInsertStatementEyetrackerSubstitution()  {
        String device = "eyetracker";
        String type = "substitution";
        String attributes = "timestamp,leftx,lefty,rightx,righty,pupilL,pupilR,task";
        String dataid = "unitTest";
        String line = "1,2,3,4,5,6,7,hi";
        Mockito.doCallRealMethod().when(deviceRepository).createInsertStatement(type,device,attributes, dataid,line);
        String statement = deviceRepository.createInsertStatement(type,device,attributes, dataid,line);
        String correctStatement = "INSERT INTO eyetracker_substitution (dataid, timestamp,leftx,lefty,rightx,righty,pupilL,pupilR,task) VALUES ('unitTest', 1, 2, 3, 4, 5, 6, 7, 'hi');";
        assertThat(statement, is(correctStatement));
    }

    @Test
    public void createInsertStatementEyetrackerAvgPupil()  {
        String device = "eyetracker";
        String type = "avgpupil";
        String attributes = "avgpupill,avgpupilr";
        String dataid = "unitTest";
        String line = "1,2";
        Mockito.doCallRealMethod().when(deviceRepository).createInsertStatement(type,device,attributes, dataid,line);
        String statement = deviceRepository.createInsertStatement(type,device,attributes, dataid,line);
        String correctStatement = "INSERT INTO eyetracker_avgpupil (dataid, avgpupill,avgpupilr) VALUES ('unitTest', 1, 2);";
        assertThat(statement, is(correctStatement));
    }


    @Test
    public void createInsertStatementEyetrackerAvgPupilTasks()  {
        String device = "eyetracker";
        String type = "avgpupiltask";
        String attributes = "avgpupill,avgpupilr,task";
        String dataid = "unitTest";
        String line = "1,2,hi";
        Mockito.doCallRealMethod().when(deviceRepository).createInsertStatement(type,device,attributes, dataid,line);
        String statement = deviceRepository.createInsertStatement(type,device,attributes, dataid,line);
        String correctStatement = "INSERT INTO eyetracker_avgpupiltask (dataid, avgpupill,avgpupilr,task) VALUES ('unitTest', 1, 2, 'hi');";
        assertThat(statement, is(correctStatement));
    }


}
