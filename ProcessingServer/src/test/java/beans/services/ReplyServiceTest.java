package beans.services;

import api.PathConstants;
import com.google.gson.Gson;
import model.ReplyMessage;
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

import javax.jms.*;
import javax.naming.InitialContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ReplyManager.class,InitialContext.class,HttpClientBuilder.class,EntityUtils.class, CloseableHttpResponse.class})
public class ReplyServiceTest {



    private final Gson gson = new Gson();

    @Mock
    private ReplyMessage replyMessage;

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



    private ReplyService replyService;

    private String message = "test";

    private String flaskReply = "flaskReply";
    private String jsonmessage;
    //    private String queue = "testQueue";
    private String replyQueue = "replyQueue";
    private String confactory = "testConnectionFactory";
//    private String[] array = {"string1\tstring2"};



    public ReplyServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    private String queue = PathConstants.REST_REPLY_QUEUE;
    private String cf = PathConstants.PROCESSING_SERVER_CONNECTION_FACTORY;

    @Before
    public void setUp() throws Exception {
        replyService = Mockito.mock(ReplyService.class);
        dataProcessService = Mockito.mock(DataProcessService.class);
        PowerMockito.mockStatic(ReplyManager.class);
        PowerMockito.mockStatic(InitialContext.class);
        PowerMockito.mockStatic(HttpClientBuilder.class);
        PowerMockito.mockStatic(EntityUtils.class);
        PowerMockito.mockStatic(CloseableHttpResponse.class);
        PowerMockito.when(ReplyManager.getInstance()).thenReturn(replyManager);


        when(InitialContext.doLookup(eq(queue))).thenReturn(destination);
        when(InitialContext.doLookup(eq(cf))).thenReturn(connectionFactory);
        when(connectionFactory.createConnection()).thenReturn(qConnection);
        when(qConnection.createQueueSession(eq(false), eq(Session.AUTO_ACKNOWLEDGE))).thenReturn(qSession);
        when(qSession.createProducer(eq(destination))).thenReturn(msgProducer);
        when(qSession.createTextMessage(anyString())).thenReturn(textMessage);

        when(HttpClientBuilder.class,"create").thenReturn(httpClientBuilder);
        when(httpClientBuilder.build()).thenReturn(httpClient);

        when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(closeableHttpResponse);
        when(result.getEntity()).thenReturn(httpEntity);






    }


    @Test
    public void processReplyReplyManagerAddReplyToList() throws NullPointerException{
        ReplyService replyService = new ReplyService();
        ReplyMessage replyMsg = setReplyMessage(Boolean.TRUE,"data","type","reply");
        String jsonReply = gson.toJson(replyMsg);
        String[] array = {jsonReply};
        when(replyManager.getArrayList()).thenReturn(array);

        String jsonMessage = gson.toJson(replyMsg);

        replyService.processReply(jsonMessage);
        verify(replyManager, times(1)).addReplyToList(eq(jsonMessage));

    }

    @Test
    public void processReplyReplyManagerGeListSize() throws NullPointerException{
        ReplyService replyService = new ReplyService();
        ReplyMessage replyMsg = setReplyMessage(Boolean.TRUE,"data","type","reply");
        String jsonReply = gson.toJson(replyMsg);
        String[] array = {jsonReply};
        when(replyManager.getArrayList()).thenReturn(array);

        String jsonMessage = gson.toJson(replyMsg);

        replyService.processReply(jsonMessage);
        verify(replyManager, times(1)).getListSize();


    }


    @Test
    public void processReplyReplyManagerGetCount() throws NullPointerException{
        ReplyService replyService = new ReplyService();
        ReplyMessage replyMsg = setReplyMessage(Boolean.TRUE,"data","type","reply");
        String jsonReply = gson.toJson(replyMsg);
        String[] array = {jsonReply};
        when(replyManager.getArrayList()).thenReturn(array);

        String jsonMessage = gson.toJson(replyMsg);

        replyService.processReply(jsonMessage);
        verify(replyManager, times(1)).getCount();


    }


    @Test
    public void processReplyReplyManagerGetArrayList() throws NullPointerException{
        ReplyService replyService = new ReplyService();
        ReplyMessage replyMsg = setReplyMessage(Boolean.TRUE,"data","type","reply");
        String jsonReply = gson.toJson(replyMsg);
        String[] array = {jsonReply};
        when(replyManager.getArrayList()).thenReturn(array);

        String jsonMessage = gson.toJson(replyMsg);

        replyService.processReply(jsonMessage);
        verify(replyManager, times(1)).getArrayList();
    }

    @Test
    public void processReplyReplyManagerClearList() throws NullPointerException{
        ReplyService replyService = new ReplyService();
        ReplyMessage replyMsg = setReplyMessage(Boolean.TRUE,"data","type","reply");
        String jsonReply = gson.toJson(replyMsg);
        String[] array = {jsonReply};
        when(replyManager.getArrayList()).thenReturn(array);

        String jsonMessage = gson.toJson(replyMsg);

        replyService.processReply(jsonMessage);
        verify(replyManager, times(1)).clearList();

    }


    public ReplyMessage setReplyMessage(Boolean success, String data, String type, String replyMsg){
        ReplyMessage replyMessage = new ReplyMessage();
        replyMessage.setSucess(success);
        replyMessage.setData(data);
        replyMessage.setReplyMessage(replyMsg);
        replyMessage.setType(type);

        return replyMessage;

    }

}
