package beans;

import model.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.jms.JMSException;
import javax.jms.TextMessage;

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
public class MessageBeanTest {


    @Mock
    private TextMessage textMessage;

    private MessageBean messageBean;

    private String message = "test";


    @Before
    public void setUp() throws JMSException {

        messageBean = Mockito.mock(MessageBean.class);
        when(textMessage.getText()).thenReturn(message);
    }


    @Test
    public void onMessageMessageReveived() throws JMSException {
        Mockito.doCallRealMethod().when(messageBean).onMessage(textMessage);
        messageBean.onMessage(textMessage);
        verify(textMessage, times(1)).getText();

    }


}
