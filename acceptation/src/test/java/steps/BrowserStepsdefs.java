package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

public class BrowserStepsdefs {

    JSONObject answer;

    @When("^the customer browse the catalog$")
    public void theCustomerBrowseTheCatalog() throws Throwable {
        String raw = WebClient.create("http://localhost:8080/catalogue/rest/catalogue/products")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(String.class);

        this.answer = new JSONArray(raw).getJSONObject(0);
    }

    @Then("^he get one article$")
    public void heGetOneArticle() throws Throwable {
        assertEquals("ramen soup", answer.getString("name"));
        assertEquals(0, answer.getInt("id"));
    }

}
