package eyetracker;

import beans.services.EyetrackerService;
import com.google.gson.Gson;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.gl.E;
import model.EyeTrackerMessage;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class SendEyetrackerSteps {
    private static final Logger logger = Logger.getLogger(SendEyetrackerSteps.class.getName());

//    private EyetrackerService eyetrackerService;
    private Gson gson = new Gson();
    private String data;
//    private Object json;
    private String json;
    private String string = null;
    private String message = null;
    private int resp;

    @Inject
    EyetrackerService eyetrackerService;

    @Before
    public void setUp(){
//        eyetrackerService = new EyetrackerService();
        Gson gson = new Gson();
    }

    @Given("^That the eyetracking data of the user is collected into \"([^\"]*)\"$")
    public void thatTheEyetrackingDataOfTheUserIsCollectedInto(String arg0) throws IOException {
        String path = "/Users/beemmess/Documents/github/thesis/Cucumber/src/test/java/eyetracker/";
        BufferedReader br = new BufferedReader(new FileReader(path+arg0));
        message = br.readLine();

    }

    @When("^The raw data is sent to the server \"([^\"]*)\"$")
    public void theRawDataIsSentToTheServer(String url) {
        logger.info(url);

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
    public void theRawDataIsSentToServer(int expectedResp) throws PendingException {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(expectedResp, resp);

    }

    @Then("^The raw data is unsuccesfully sent to the server and respond code is <(\\d+)>$")
    public void theRawDataIsUnsuccesfulSentToServer(int expectedResp) throws PendingException {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(expectedResp, resp);

    }

    @When("^The raw data is sent through the cleaning process$")
    public void theRawDataIsSentThroughTheCleaningProcess() throws NullPointerException{
        // Write code here that turns the phrase above into concrete actions
//        EyeTrackerMessage eyeTrackerMessage = eyetrackerService.preProcessMessage(message);
//        json = gson.toJson(eyeTrackerMessage, EyeTrackerMessage.class);
//        Boolean test = eyetrackerService.test();
//        logger.info(test.toString());

    }

    @Then("^I the data should look like this after cleaning \"([^\"]*)\"$")
    public void iTheDataShouldLookLikeThisAfterCleaning(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
//        String path = "/Users/beemmess/Documents/github/thesis/Cucumber/src/test/java/eyetracker/";
//        BufferedReader br = new BufferedReader(new FileReader(path+arg0));
//        message = br.readLine();
//        logger.info(message);
//        assertEquals(json,message);
    }
}
