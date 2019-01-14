package beans.services;


import api.PathConstants;
import com.google.gson.Gson;
import model.DataMessage;
import model.Message;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import reply.ReplyManager;

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
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Arrays;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ReplyManager.class,InitialContext.class,HttpClientBuilder.class,EntityUtils.class, CloseableHttpResponse.class})
public class DataServiceTest {

    private final Gson gson = new Gson();

    @Mock
    private DataMessage dataMessage;

//    @Mock
//    private String message;

    @Mock
    private ReplyManager replyManager;

    @Mock
    private DataProcessService dataProcessService;

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

    @Mock
    private TextMessage textMessage;

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private CloseableHttpResponse closeableHttpResponse;

    @Mock
    private HttpClientBuilder httpClientBuilder;

    @Mock
    private HttpPost request;

    @Mock
    private StringEntity params;

    @Mock
    private HttpResponse result;

    @Mock
    private EntityUtils entityUtils;

    @Mock
    private HttpEntity httpEntity;

    @Mock
    private TextMessage receivedTextMessage;


    private DataService dataService;

    private String message = "test";

    private String flaskReply = "flaskReply";
    private String jsonmessage;
//    private String queue = "testQueue";
    private String replyQueue = "replyQueue";
    private String confactory = "testConnectionFactory";


    public DataServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    private String queue = PathConstants.DATABASE_QUEUE;

    private String cf = PathConstants.DATABASE_SERVER_CONNECTION_FACTORY;

    @Before
    public void setUp() throws Exception {
        dataService = Mockito.mock(DataService.class);
        dataProcessService = Mockito.mock(DataProcessService.class);
        PowerMockito.mockStatic(ReplyManager.class);
        PowerMockito.mockStatic(InitialContext.class);
        PowerMockito.mockStatic(HttpClientBuilder.class);
        PowerMockito.mockStatic(EntityUtils.class);
        PowerMockito.mockStatic(CloseableHttpResponse.class);
        PowerMockito.when(ReplyManager.getInstance()).thenReturn(replyManager);

//        String[] apiUrls = {"one"};
//        when(dataMessage.getApiUrl()).thenReturn(String.valueOf(apiUrls));
//
//        when(dataProcessService.postToFlask(anyString(),anyString())).thenReturn(flaskReply);
//        when(PathConstants.DATABASE_QUEUE).thenReturn("db-queue");
//        when(PathConstants.DATABASE_SERVER_CONNECTION_FACTORY).thenReturn("cf");

//        Mockito.doCallRealMethod().when(dataProcessService).sendDataToDestination(anyString(),anyString(),anyString());
//        dataProcessService.sendDataToDestination("test","test","test");
//        Mockito.doCallRealMethod().when(dataProcessService).postToFlask(anyString(),anyString());




        when(InitialContext.doLookup(eq(queue))).thenReturn(destination);
        when(InitialContext.doLookup(eq(cf))).thenReturn(connectionFactory);
        when(connectionFactory.createConnection()).thenReturn(qConnection);
        when(qConnection.createQueueSession(eq(false), eq(Session.AUTO_ACKNOWLEDGE))).thenReturn(qSession);
        when(qSession.createProducer(eq(destination))).thenReturn(msgProducer);
        when(qSession.createTextMessage(anyString())).thenReturn(textMessage);

        when(HttpClientBuilder.class,"create").thenReturn(httpClientBuilder);
        when(httpClientBuilder.build()).thenReturn(httpClient);
//        when(HttpPost.class).thenReturn(request);
//        when(EntityUtils.class,"toString").thenReturn(flaskReply);
        when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(closeableHttpResponse);
        when(result.getEntity()).thenReturn(httpEntity);
        when(dataService.postToFlask(anyString(),anyString())).thenReturn("test");






    }


    @Test
    public void processMessageReplyManagerClearListTest() throws NullPointerException{
        DataService dataService = new DataService();

        DataMessage dataMsg = setDataMessage("1","a1", "1", "type", "url1,url2", "device");
        String jsonMessage = gson.toJson(dataMsg);

        dataService.processMessage(jsonMessage);
        verify(replyManager, times(1)).clearList();

    }

    @Test
    public void processMessageReplyManagerClearCountTest() throws NullPointerException{
        DataService dataService = new DataService();

        DataMessage dataMsg = setDataMessage("1","a1", "1", "type", "url1,url2", "device");
        String jsonMessage = gson.toJson(dataMsg);

        dataService.processMessage(jsonMessage);
        verify(replyManager, times(1)).clearCount();
    }

    @Test
    public void processMessageReplyManagerSetCountTest() throws NullPointerException{
        DataService dataService = new DataService();

        DataMessage dataMsg = setDataMessage("1","a1", "1", "type", "url1,url2", "device");
        String jsonMessage = gson.toJson(dataMsg);

        dataService.processMessage(jsonMessage);
        verify(replyManager, times(1)).setCount(anyInt());
    }



//    @Test
//    public void processMessageSendDataToDestination() throws NullPointerException{
//        DataService dataService = new DataService();
//
//        DataMessage dataMsg = setDataMessage("1","a1", "1", "type", "url1,url2", "device");
//        String jsonMessage = gson.toJson(dataMsg);
//
//        dataService.processMessage(jsonMessage);
//        verify(dataProcessService, times(1)).sendDataToDestination(anyString(),anyString(), anyString());
//    }

    @Test
    public void dataProcessServiceCreatesConnection() throws JMSException {
        DataService dataService = new DataService();

        DataMessage dataMsg = setDataMessage("1","a1", "1", "type", "url1,url2", "device");
        String jsonMessage = gson.toJson(dataMsg);

        dataService.processMessage(jsonMessage);
        verify(connectionFactory, times(3)).createConnection();
    }

    @Test
    public void dataProcessServiceCreatesProducer() throws JMSException {
        DataService dataService = new DataService();

        DataMessage dataMsg = setDataMessage("1","a1", "1", "type", "url1,url2", "device");
        String jsonMessage = gson.toJson(dataMsg);

        dataService.processMessage(jsonMessage);
        verify(qConnection, times(3)).createQueueSession(eq(false), eq(Session.AUTO_ACKNOWLEDGE));
    }

    @Test
    public void dataProcessServiceCreatesTextMessage() throws JMSException {
        DataService dataService = new DataService();

        DataMessage dataMsg = setDataMessage("1","a1", "1", "type", "url1,url2", "device");
        String jsonMessage = gson.toJson(dataMsg);

        dataService.processMessage(jsonMessage);
        verify(qSession, times(1)).createTextMessage(eq(jsonMessage));
    }

    @Test
    public void dataProcessServiceProducerSendsMessage() throws JMSException {
        DataService dataService = new DataService();

        DataMessage dataMsg = setDataMessage("1","a1", "1", "type", "url1,url2", "device");
        String jsonMessage = gson.toJson(dataMsg);

        dataService.processMessage(jsonMessage);
        verify(msgProducer, times(1)).send(eq(textMessage));
    }

    @Test
    public void dataProcessServiceProducerClose() throws JMSException {
        DataService dataService = new DataService();

        DataMessage dataMsg = setDataMessage("1","a1", "1", "type", "url1,url2", "device");
        String jsonMessage = gson.toJson(dataMsg);

        dataService.processMessage(jsonMessage);
        verify(msgProducer, times(3)).close();
    }


    public DataMessage setDataMessage(String id, String attributes, String data, String type, String apiUrl, String device){
        DataMessage dataMessage = new DataMessage();
        dataMessage.setId(id);
        dataMessage.setAttributes(attributes);
        dataMessage.setData(data);
        dataMessage.setType(type);
        dataMessage.setApiUrl(apiUrl);
        dataMessage.setDevice(device);
        return dataMessage;

    }


}
