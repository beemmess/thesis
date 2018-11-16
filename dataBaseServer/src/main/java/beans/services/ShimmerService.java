package beans.services;


import client.ShimmerClient;
import client.domain.Shimmer;
import com.google.gson.Gson;
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

    public void saveDataToDB(String message){
        ShimmerMessage shimmerMessage = gson.fromJson(message, ShimmerMessage.class);
        String type = shimmerMessage.getType();
        if(type.equals("raw")) {
            logger.info(type);
            saveRawData(shimmerMessage);
        }
        else if(type.equals("normalized")){
            logger.info(type);
            saveNormalizedData(shimmerMessage);
        }
        else{
            logger.info("type not found: ");
        }


    }

    private void saveNormalizedData(ShimmerMessage shimmerMessage) {

        try(BufferedReader br = new BufferedReader(new StringReader(shimmerMessage.getData()))){
            String line;
            while((line = br.readLine()) != null ){
                String[] values = line.split(",");
                Shimmer shimmer = new Shimmer(shimmerMessage.getId(), Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]),values[3]);
                shimmerClient.shimmerInsertNormalizedValues(shimmer);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveRawData(ShimmerMessage shimmerMessage) {


        try(BufferedReader br = new BufferedReader(new StringReader(shimmerMessage.getData()))){
            String line;
            while((line = br.readLine()) != null ){
                String[] values = line.split(",");
                Shimmer shimmer = new Shimmer(shimmerMessage.getId(), Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]),values[3]);
                shimmerClient.shimmerInsertRawValues(shimmer);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
