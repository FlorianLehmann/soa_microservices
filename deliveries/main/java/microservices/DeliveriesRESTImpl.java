package microservices;

import javax.ws.rs.Path;
import java.util.List;

@Path("/rest/Deliveries")
public class DeliveriesRESTImpl implements DeliveriesREST {

    /*public ETA getETA(int productId, String address) {
        return new ETA(productId, address);
    }

    public String validateProduct(int productId, String customerName, String customerAddress) {
        return null;
    }*/

    @Override
    public List<Integer> getDeliveries(int deliveryManId) {
        return null;
    }

    @Override
    public String validateDelivery(int commandId, int deliveryManId) {
        return null;
    }
}
