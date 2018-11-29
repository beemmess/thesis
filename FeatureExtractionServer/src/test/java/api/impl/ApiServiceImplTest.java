package api.impl;


import api.ApiResponseMessage;
import api.PathConstants;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
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
@PrepareForTest({InitialContext.class,Response.class})
public class ApiServiceImplTest{

    @Mock
    private ConnectionFactory connectionFactory;
    @Mock
    private Destination destination;
    @Mock
    private Destination replyDestination;
    @Mock
    private QueueConnection qConnection;
    @Mock
    private QueueSession qSession;
    @Mock
    private MessageConsumer msgConsumer;
    @Mock
    private MessageProducer msgProducer;
    @Mock
    private TextMessage textMessage;
    @Mock
    private Response.ResponseBuilder responseBuilderOk;
    @Mock
    private Response.ResponseBuilder responseBuilderError;
    @Mock
    private Response okResponse;
    @Mock
    private TextMessage receivedTextMessage;
    @Mock
    private Response errorResponse;

    private String jsonmessage;
    private ApiServiceImpl apiServiceImpl;
    private String queue = "testQueue";
    private String replyQueue = "replyQueue";
    private String confactory = "testConnectionFactory";

    private Gson gson = new Gson();

    public ApiServiceImplTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void setup() throws NamingException, JMSException {
        PowerMockito.mockStatic(InitialContext.class);
        PowerMockito.mockStatic(Response.class);

        jsonmessage = "some Json String";
        when(InitialContext.doLookup(eq(queue))).thenReturn(destination);
        when(InitialContext.doLookup(eq(confactory))).thenReturn(connectionFactory);
        when(InitialContext.doLookup(eq(PathConstants.REST_REPLY_QUEUE))).thenReturn(replyDestination);

        when(connectionFactory.createConnection()).thenReturn(qConnection);
        when(qConnection.createQueueSession(eq(false), eq(Session.AUTO_ACKNOWLEDGE))).thenReturn(qSession);
        when(qSession.createProducer(eq(destination))).thenReturn(msgProducer);
        when(qSession.createTextMessage(anyString())).thenReturn(textMessage);
        when(qSession.createConsumer(eq(replyDestination))).thenReturn(msgConsumer);
        when(msgConsumer.receive()).thenReturn(receivedTextMessage);

        when(Response.ok()).thenReturn(responseBuilderOk);
        when(responseBuilderOk.entity(any(ApiResponseMessage.class))).thenReturn(responseBuilderOk);
        when(responseBuilderOk.build()).thenReturn(okResponse);
        when(Response.serverError()).thenReturn(responseBuilderError);
        when(responseBuilderError.entity(any(ApiResponseMessage.class))).thenReturn(responseBuilderError);
        when(responseBuilderError.build()).thenReturn(errorResponse);


        apiServiceImpl = new ApiServiceImpl(queue,confactory);

    }

    @Test
    public void assertThatconstructorIsNotNull() {
        assertThat(apiServiceImpl, is(notNullValue()));
    }

    @Test
    public void responseCreatesConnection() throws JMSException {
        when(receivedTextMessage.getText()).thenReturn(jsonmessage);
        apiServiceImpl.response("test");
        verify(connectionFactory, times(1)).createConnection();
    }

    @Test
    public void responseCreatesProducer() throws JMSException {
        when(receivedTextMessage.getText()).thenReturn(jsonmessage);
        apiServiceImpl.response("test");
        verify(qConnection, times(1)).createQueueSession(eq(false), eq(Session.AUTO_ACKNOWLEDGE));
    }

    @Test
    public void responseCreatesTextMessage() throws JMSException {
        when(receivedTextMessage.getText()).thenReturn(jsonmessage);
        apiServiceImpl.response("test");
        verify(qSession, times(1)).createTextMessage(eq("test"));
    }

    @Test
    public void responseMessageProducerSendsMessage() throws JMSException {
        when(receivedTextMessage.getText()).thenReturn(jsonmessage);
        apiServiceImpl.response("test");
        verify(msgProducer, times(1)).send(eq(textMessage));
    }


}
