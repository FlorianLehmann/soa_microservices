package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.runner.RunWith;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

public class ValidationStepsdefs {

    private String name;
    private String address;
    private int productId;
    private JSONObject ans;

    private String url = "http://localhost:8081/validation/rest/validation/products/{productId}/eta/{address}";

    private JSONObject callGet(JSONObject request) {
        String raw =
                WebClient.create(this.url)
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .get(String.class);
        return new JSONObject(raw);
    }


    @Given("^a client named (.*) at (.*)$")
    public void aClientNamedArthurAtBiot(String name, String address) throws Throwable {
        this.name = name;
        this.address = address;
    }

    @And("^a ramen soup$")
    public void aRamenSoup() throws Throwable {
        this.productId = 0;
    }

    @When("^he ask for an ETA$")
    public void heAskForAnETA() throws Throwable {
        String raw =
                WebClient.create("http://localhost:8081/validation/rest/validation/products/" + this.productId + "/eta/" + this.address)
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .get(String.class);

        this.ans = new JSONObject(raw);
    }

    @Then("^he get an ETA of (\\d+) min$")
    public void heGetAnETAOfMin(int time) throws Throwable {
        assertEquals(time, ans.getInt("minutes"));
    }

    @When("^he validate his product$")
    public void heValidateHisProduct() throws Throwable {
        JSONObject request = new JSONObject();
        request.put("productId", this.productId)
                .put("name", this.name)
                .put("address", this.address);

        String raw =
                WebClient.create("http://localhost:8081/validation/rest/validation/validate/product")
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(request.toString(), String.class);

    }

    @Then("^the order is send to the restaurant$")
    public void theOrderIsSendToTheRestaurant() throws Throwable {
        String raw = WebClient.create("http://localhost:8083/orders/rest/orders/restaurants/0/orders")
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .get(String.class);

        this.ans = new JSONArray(raw).getJSONObject(0);
        assertEquals(0, ans.getInt("productId"));
        assertEquals(0, ans.getInt("restaurantId"));
    }

    @And("^a deliveryman is assign for the task$")
    public void aDeliverymanIsAssignForTheTask() throws Throwable {
        String raw = WebClient.create("http://localhost:8082/deliveries/rest/deliveries/deliveryman/0")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get(String.class);

        this.ans = new JSONArray(raw).getJSONObject(0);
        System.out.println(this.ans);
        assertEquals(0, ans.getInt("deliveryManId"));
    }

}
