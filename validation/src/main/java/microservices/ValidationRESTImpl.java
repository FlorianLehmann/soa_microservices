package microservices;

import javax.ws.rs.Path;

@Path("/rest/calculator")
public class ValidationRESTImpl implements ValidationREST {

    public String getETA(int productId, String address) {
        return "{" +
                "   time: 35min" +
                "}";
    }

    public String validateProduct(int productId, String customerName, String customerAddress) {
        return null;
    }
}
