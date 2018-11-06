package eyetracker;

import client.CassandraClient;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class SendEyetrackerSteps {
    private static final Logger logger = Logger.getLogger(SendEyetrackerSteps.class.getName());

    private Eyetracker eyetracker;
    private Gson gson = new Gson();
    private String data;
    private Object json;
    private String string = null;
    private String message;
    private int resp;


    @Given("^That the eyetracking data of the user is collected into \"([^\"]*)\"$")
    public void thatTheEyetrackingDataOfTheUserIsCollectedInto(String arg0) throws IOException {
        String path = "/Users/beemmess/Documents/github/thesis/Cucumber/src/test/java/eyetracker/";
        BufferedReader br = new BufferedReader(new FileReader(path+arg0));
        message = br.readLine();

    }

    @When("^The raw data is sent to the server \"([^\"]*)\"$")
    public void theRawDataIsSentToTheServer(String url) {


        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(message);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);
            resp = result.getStatusLine().getStatusCode();

        } catch (IOException ex) {
            logger.info(ex.toString());
        }
    }

    @Then("^The raw data is succesfully sent to the server and respond code is <(\\d+)>$")
    public void theRawDataIsSavedToDatabase(int expectedResp) {
        // Write code here that turns the phrase above into concrete actions

        CassandraClient cassandraClient = new CassandraClient();
        cassandraClient.CassandraClient();
        String s = cassandraClient.getDataFromUserId("bjarki");
        logger.info(s);
        assertEquals(expectedResp, resp);


    }

}
