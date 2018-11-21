package beans.services;


import client.ShimmerClient;
import client.domain.Shimmer;
import com.google.gson.Gson;
import model.ReplyMessage;
import model.ShimmerMessage;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

@Named
@ApplicationScoped
public class ShimmerService {

    private static final Logger logger = Logger.getLogger(ShimmerService.class.getName());


    private Gson gson = new Gson();
    private ShimmerClient shimmerClient = new ShimmerClient();
    private Boolean response;

    private ReplyMessage replyMessage = new ReplyMessage();

    private String ERROR_RESPONSE = "Something went wrong, data was not saved to database";
    private String DATA_SAVED = "Data saved to database";

    public String saveDataToDB(String message){
        ShimmerMessage shimmerMessage = gson.fromJson(message, ShimmerMessage.class);
        String type = shimmerMessage.getType();
        if(type.equals("raw")) {
            logger.info(type);
            return saveRawData(shimmerMessage);
        }
        else if(type.equals("normalize")){
            logger.info(type);
            return saveNormalizedData(shimmerMessage);
        }
        else{
            logger.info("type not found: ");
            return createJsonStringResponse(ERROR_RESPONSE,"InvalidData");
        }


    }

    private String saveNormalizedData(ShimmerMessage shimmerMessage) {
        replyMessage.setData(shimmerMessage.getType());

        try(BufferedReader br = new BufferedReader(new StringReader(shimmerMessage.getData()))){
            String line;
            while((line = br.readLine()) != null ){
                String[] values = line.split(",");
                Shimmer shimmer = new Shimmer(shimmerMessage.getId(), Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]),values[3]);
                response = shimmerClient.shimmerInsertNormalizedValues(shimmer);
                if(!response){
                    return createJsonStringResponse(ERROR_RESPONSE,shimmerMessage.getType());
                }
            }
            return createJsonStringResponse(DATA_SAVED,shimmerMessage.getType());

        } catch (IOException e) {
            logger.error(e.toString());
            return createJsonStringResponse(ERROR_RESPONSE,shimmerMessage.getType());
        }
    }

    public String saveRawData(ShimmerMessage shimmerMessage) {
        replyMessage.setData(shimmerMessage.getType());

        try(BufferedReader br = new BufferedReader(new StringReader(shimmerMessage.getData()))){
            String line;
            while((line = br.readLine()) != null ){
                String[] values = line.split(",");
                Shimmer shimmer = new Shimmer(shimmerMessage.getId(), Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]),values[3]);
                response = shimmerClient.shimmerInsertRawValues(shimmer);
                if(!response){
                    return createJsonStringResponse(ERROR_RESPONSE,shimmerMessage.getType());
                }
            }
            return createJsonStringResponse(DATA_SAVED,shimmerMessage.getType());



        } catch (IOException e) {
            logger.error(e.toString());
            return createJsonStringResponse(ERROR_RESPONSE,shimmerMessage.getType());
        }
    }

    public String createJsonStringResponse(String message, String data){
        replyMessage.setReplyMessage(message);
        replyMessage.setData(data);
        return gson.toJson(replyMessage);
    }
}
