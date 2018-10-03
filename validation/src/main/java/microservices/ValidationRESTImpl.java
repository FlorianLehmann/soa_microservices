package microservices;

import model.ETA;

import javax.ws.rs.Path;

@Path("/rest/validation")
public class ValidationRESTImpl implements ValidationREST {

    public ETA getETA(int productId, String address) {
        return new ETA(productId, address);
    }

    public String validateProduct(int productId, String customerName, String customerAddress) {
        return null;
    }
}
