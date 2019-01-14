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

        DataMessage dataMessage = gson.fromJson(message, DataMessage.class);
        String[] apiUrls = dataMessage.getApiUrl().split(",");
        replyManager.clearList();
        replyManager.clearCount();
        replyManager.setCount(apiUrls.length+1);

//        Send raw data to database
        sendDataToDestination(message, PathConstants.DATABASE_QUEUE, PathConstants.DATABASE_SERVER_CONNECTION_FACTORY);
//        Send raw data to python web client for processing and then send the processed data to database
        for(String apiUrl : apiUrls){
            String url = "http://" + address + ":" + port + apiUrl;
            msg = postToFlask(message, url);
            sendDataToDestination(msg,PathConstants.DATABASE_QUEUE, PathConstants.DATABASE_SERVER_CONNECTION_FACTORY);
        }


    }


}