package beans.services;


import databaseClient.DeviceClient;
import com.google.gson.Gson;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import model.DataMessage;
import model.ReplyMessage;
import org.jboss.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * A Class that prepares the data for inserting into the database as well as generating
 * the 'create table' statement with information gathering from the JSON string in the
 * incoming messages.
 */
@Named
@ApplicationScoped
public class DataService {
    private static final Logger logger = Logger.getLogger(DataService.class.getName());


    private Gson gson = new Gson();
    private DeviceClient eyetrackerClient = new DeviceClient();
    private ReplyMessage replyMessage = new ReplyMessage();
    private Boolean response;

    private String ERROR_RESPONSE = "Something went wrong, data was not saved to database";
    private String DATA_SAVED = "Data saved to database";

    /**
     * A function that deserialize the json string to further process the incoming message
     * @param message
     * @return message reply
     */
    public String saveDataToDB(String message) {

        DataMessage dataMessage = gson.fromJson(message, DataMessage.class);
        String type = dataMessage.getType();
        String attributes = dataMessage.getAttributes();
        String device = dataMessage.getDevice();
        eyetrackerClient.createTable(device,type,attributes);
        return saveData(dataMessage);
    }

    /**
     * A methond that takes the JSON string deserilised and iterates throught the data, forwarding data
     * to the database client for storage
     * @param dataMessage
     * @return message reply with information of successful insterted statements.
     */
    public String saveData(DataMessage dataMessage){

        String type = dataMessage.getType();
        String dataid = dataMessage.getId();
        String attributes = dataMessage.getAttributes();
        String device = dataMessage.getDevice();

        try (BufferedReader br = new BufferedReader(new StringReader(dataMessage.getData()))) {
            String line;
            while ((line = br.readLine()) != null) {
                response = eyetrackerClient.insertData(type,device,attributes,dataid,line);
                if(!response){
                    return createJsonStringResponse(ERROR_RESPONSE, dataMessage.getType(), false);
                }
            }
            return createJsonStringResponse(DATA_SAVED, dataMessage.getType(), true);

        } catch(IOException e) {
            logger.warn("error in Save raw Data " + e.toString());
            return createJsonStringResponse(ERROR_RESPONSE, dataMessage.getType(), false);
        }
    }


    /**
     * A method that generates a replyMessage object that contains information of successful
     * inserted data to the database
     * @param message
     * @param data
     * @param success
     * @return JSON string
     */
    public String createJsonStringResponse(String message, String data, Boolean success){
        replyMessage.setReplyMessage(message);
        replyMessage.setData(data);
        replyMessage.setSucess(success);
        return gson.toJson(replyMessage);
    }


}
