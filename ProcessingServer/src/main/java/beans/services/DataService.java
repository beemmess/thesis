package beans.services;

import api.PathConstants;
import com.google.gson.Gson;
import model.DataMessage;
import org.jboss.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import reply.ReplyManager;


@Named
@ApplicationScoped
public class DataService extends DataProcessService {

    private static final Logger logger = Logger.getLogger(DataService.class.getName());
    private ReplyManager replyManager = ReplyManager.getInstance();

    private String msg;
    private String address = PathConstants.DOCKER_LOCAL_NETWORK;     // Local server ip address
    private String port = PathConstants.PYTHON_WEB_CLIENT_PORT; // port of the python web client
    private Gson gson = new Gson();

    public DataService(){}


    public void processMessage(String message){

//        System.out.println("am I here 1");
//        System.out.println(message);
        DataMessage dataMessage = gson.fromJson(message, DataMessage.class);
//        System.out.println("am I here 2");
        String[] apiUrls = dataMessage.getApiUrl().split(",");
//        System.out.println("am I here 3");
        replyManager.clearList();
//        System.out.println("am I here 4");
        replyManager.clearCount();
//        System.out.println("am I here 5");
        replyManager.setCount(apiUrls.length+1);
//        System.out.println("am I here 6");
//        System.out.println(PathConstants.DATABASE_QUEUE);

//        Send raw data to database
        sendDataToDestination(message, PathConstants.DATABASE_QUEUE, PathConstants.DATABASE_SERVER_CONNECTION_FACTORY);
//        System.out.println("am I here 7");
//        Send raw data to python web client for processing and then send the processed data to database
        for(String apiUrl : apiUrls){
//            System.out.println("am I here 8");
            String url = "http://" + address + ":" + port + apiUrl;
            msg = postToFlask(message, url);
//            System.out.println("am I here 9");
            sendDataToDestination(msg,PathConstants.DATABASE_QUEUE, PathConstants.DATABASE_SERVER_CONNECTION_FACTORY);
        }


    }


}