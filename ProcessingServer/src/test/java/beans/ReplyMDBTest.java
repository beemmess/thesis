package beans;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class ReplyMDBTest {



    @Mock
    private ReplyMDB replyMDB;


    private String message = "message";


    @Test
    public void messageReceivedVerifyMessageExchange(){

        replyMDB.messageReceived(message);
        verify(replyMDB,times(1)).messageReceived(eq(message));
    }

}
