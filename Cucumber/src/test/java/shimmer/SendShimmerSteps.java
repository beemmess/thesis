package shimmer;

import com.google.gson.Gson;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import model.DataMessage;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("Duplicates")
public class SendShimmerSteps {
    private static final Logger logger = Logger.getLogger(SendShimmerSteps.class.getName());

    private Gson gson = new Gson();
    private int resp;
    private String json;
    private DataMessage dataMessage;


    @Given("^That the shimmer data of the user is collected and sent as a JSON string to the server:$")
    public void thatTheShimmerDataOfTheUserIsCollectedAndSentAsAJsonstringToTheServer(String json) {
        this.json = json;
    }

    @When("^The raw data is sent to the server \"([^\"]*)\"$")
    public void theRawDataIsSentToTheServer(String url) {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(json);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);
            resp = result.getStatusLine().getStatusCode();

        } catch (IOException ex) {
            logger.info(ex.toString());
        }
    }


    @Then("^The data is succesfully sent and the server code response should be <(\\d+)>$")
    public void theRawDataIsSentToServer(int expectedResp) throws PendingException {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(expectedResp, resp);

    }

    @Then("^The raw data is unsuccesfully sent to the server and respond code is <(\\d+)>$")
    public void theRawDataIsUnsuccesfulSentToServer(int expectedResp) throws PendingException {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(expectedResp, resp);

    }


    @Given("^That the shimmer data of the user has been sent to the server as a JSON:$")
    public void thatTheShimmerDataOfTheUserHasBeenSentToTheServerAsAJson(String json) {
        this.json = json;
    }


    @When("^The raw data is sent through a \"([^\"]*)\" process at \"([^\"]*)\"$")
    public void theRawDataIsSentThroughATypeProcessAt(String type, String url) {
        // Write code here that turns the phrase above into concrete actions

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(json);

            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            httpClient.execute(request);
            HttpResponse result = httpClient.execute(request);

            json = EntityUtils.toString(result.getEntity(), "UTF-8");
        } catch (IOException ex) {
            logger.info(ex.toString());
        }
        dataMessage = gson.fromJson(json, DataMessage.class);


    }

    @Then("^The JSON value of type should be \"([^\"]*)\"$")
    public void theJSONValueOfTypeShouldBeType(String type) {
        assertEquals(type, dataMessage.getType());

    }

    @And("^The JSON value of id should be \"([^\"]*)\"$")
    public void theJSONValueOfIdShouldBeId(String id)  {
        assertEquals(id, dataMessage.getId());

    }

    @And("^The JSON value of features should be \"([^\"]*)\"$")
    public void theJSONValueOfFeaturesShouldBeFeatures(String features) {
        assertEquals(features,dataMessage.getFeatures());
    }

    @And("^The JSON value of data should be \"([^\"]*)\"$")
    public void theJSONValueOfDataShouldBeData(String data) {
        assertEquals(data, dataMessage.getData());
    }
}
