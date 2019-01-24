package beans;


import beans.services.DataService;
import model.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

// Hamcrest mathcers
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

//import static org.mockito.Matchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;

@RunWith(PowerMockRunner.class)
public class DataMDBtest {

    @Mock
    private DataMDB dataMDB;

    private String message = "message";


    @Test
    public void messageRecievedVerifyMessageExchange(){

        dataMDB.messageReceived(message);
        verify(dataMDB,times(1)).messageReceived(eq(message));
    }




}
