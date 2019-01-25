package beans;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;


@RunWith(PowerMockRunner.class)
public class DataMDBTest {

    @Mock
    private DataMDB dataMDB;

    private String message = "message";




    @Test
    public void messageRecievedVerifyMessageExchange(){

        dataMDB.messageReceived(message);
        verify(dataMDB,times(1)).messageReceived(eq(message));
    }





}
