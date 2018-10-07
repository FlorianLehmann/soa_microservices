package microservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Delivery;
import model.ETA;
import model.Order;

import javax.ws.rs.Path;
import java.io.IOException;

@Path("/rest/validation")
public class ValidationRESTImpl implements ValidationREST {

    public ETA getETA(int productId, String address) {
        return new ETA(productId, address);
    }

    public void validateProduct(int productId, String customerName, String customerAddress) {
        Client client = new Client();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            client.sendPost(objectMapper.writeValueAsString(new Order(productId, 0)),"http://orders:8080/orders/rest/orders/neworder");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.sendPost(objectMapper.writeValueAsString(new Delivery(customerAddress)),"http://deliveries:8080/deliveries/rest/deliveries/adddelivery");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
