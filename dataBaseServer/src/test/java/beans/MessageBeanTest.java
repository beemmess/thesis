package beans;

import model.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
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
@PrepareForTest({InitialContext.class})
public class MessageBeanTest {


    @Mock
    private TextMessage textMessage;

    @Mock
    private Destination destination;

    @Mock
    private ConnectionFactory connectionFactory;

    @Mock
    private QueueConnection qConnection;

    @Mock
    private QueueSession qSession;

    @Mock
    private MessageProducer msgProducer;

    private MessageBean messageBean;

    private String message = "test";

    private String queue = PathConstants.REPLY_QUEUE;
    private String cf = PathConstants.PROCESSING_SERVER_CONNECTION_FACTORY;
    private String replyMsg = "replyMsg";

    public MessageBeanTest() {
        MockitoAnnotations.initMocks(this);
    }


    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(InitialContext.class);


        messageBean = Mockito.mock(MessageBean.class);
        when(textMessage.getText()).thenReturn(message);
        when(messageBean.messageReceived(message)).thenReturn(replyMsg);


        when(InitialContext.doLookup(eq(queue))).thenReturn(destination);
        when(InitialContext.doLookup(eq(cf))).thenReturn(connectionFactory);
        when(connectionFactory.createConnection()).thenReturn(qConnection);
        when(qConnection.createQueueSession(eq(false), eq(Session.AUTO_ACKNOWLEDGE))).thenReturn(qSession);
        when(qSession.createProducer(eq(destination))).thenReturn(msgProducer);
        when(qSession.createTextMessage(anyString())).thenReturn(textMessage);

    }


    @Test
    public void onMessageMessageReveived() throws JMSException {
        Mockito.doCallRealMethod().when(messageBean).onMessage(textMessage);
        messageBean.onMessage(textMessage);
        verify(textMessage, times(1)).getText();

    }

    @Test
    public void onMessageCreatesConnection() throws JMSException {
        Mockito.doCallRealMethod().when(messageBean).onMessage(textMessage);
        messageBean.onMessage(textMessage);

        verify(connectionFactory, times(1)).createConnection();
    }

    @Test
    public void onMessageCreatesProducer() throws JMSException {
        Mockito.doCallRealMethod().when(messageBean).onMessage(textMessage);
        messageBean.onMessage(textMessage);
        verify(qConnection, times(1)).createQueueSession(eq(false), eq(Session.AUTO_ACKNOWLEDGE));
    }

    @Test
    public void onMessageCreatesTextMessage() throws JMSException {
        Mockito.doCallRealMethod().when(messageBean).onMessage(textMessage);
        messageBean.onMessage(textMessage);
        verify(qSession, times(1)).createTextMessage(eq(replyMsg));
    }

    @Test
    public void onMessageProducerSendsMessage() throws JMSException {
        Mockito.doCallRealMethod().when(messageBean).onMessage(textMessage);
        messageBean.onMessage(textMessage);
        verify(msgProducer, times(1)).send(eq(textMessage));
    }

    @Test
    public void onMessageProducerClose() throws JMSException {
        Mockito.doCallRealMethod().when(messageBean).onMessage(textMessage);
        messageBean.onMessage(textMessage);
        verify(msgProducer, times(1)).close();
    }

}
