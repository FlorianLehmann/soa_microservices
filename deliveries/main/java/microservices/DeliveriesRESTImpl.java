package microservices;

import javax.ws.rs.Path;
import java.util.List;

@Path("/rest/Deliveries")
public class DeliveriesRESTImpl implements DeliveriesREST {

    @Override
    public List<Integer> getDeliveries(int deliveryManId) {
        return null;
    }

    @Override
    public String validateDelivery(int commandId, int deliveryManId, String addressClient) {
        return null;
    }
}
