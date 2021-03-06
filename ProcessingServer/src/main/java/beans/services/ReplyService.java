package beans.services;

import api.PathConstants;
import com.google.gson.Gson;
import model.ReplyMessage;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import reply.ReplyManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.jms.*;

/**
 * A Class that handles the replies from the Database Server
 */
@Named
@ApplicationScoped
public class ReplyService extends DataProcessService {
    private static final Logger logger = Logger.getLogger(ReplyService.class.getName());

    private ReplyManager replyManager = ReplyManager.getInstance();

    private ConnectionFactory connectionFactory;
    private Destination destination;
    private QueueSession session;
    private QueueConnection con;
    private Gson gson = new Gson();

    /**
     * A method that handles the incoming messages from the message bean that comes from the database server
     * @param message
     */
    public void processReply(String message){
    replyManager.addReplyToList(message);
    

    if(replyManager.getCount()==replyManager.getListSize()){
        

        Object[] array = replyManager.getArrayList();
        String arrayMessage = StringUtils.join(array,"\t");
        String[] replyMsgList = arrayMessage.split("\t");
        String replyMessage = generateReplyMessage(replyMsgList);
        logger.info(replyMessage);

        sendDataToDestination(replyMessage, PathConstants.REST_REPLY_QUEUE, PathConstants.PROCESSING_SERVER_CONNECTION_FACTORY);
        replyManager.clearList();

        }
    }


    /**
     * A helper method that generates the reply message to reply to the API
     * @param list
     * @return
     */
    public String generateReplyMessage(String[] list) {
        
        int len = list.length;
        String msg ="";
        int successCount = 0;
        for (String reply : list){
            ReplyMessage replyMessage = gson.fromJson(reply, ReplyMessage.class);
            Boolean success = replyMessage.getSucess();
            msg += replyMessage.getData()+ " " + replyMessage.getReplyMessage() +": "+ replyMessage.getSucess()+"\n";
            if(success){
                successCount +=1;
            }
        }
        if(successCount == len){
            return "All data has been sucessfully saved to database:\n" + msg;
        }
        return "Error in saving in database:\n" + msg;
    }



}
