package microservices;

import javax.ws.rs.Path;
import java.util.List;

@Stateless
@Path("/rest/Deliveries")
public class DeliveriesRESTImpl implements DeliveriesREST {

    @PersistenceContext(unitName="deliveries")
    private EntityManager entityManager;

    @Override
    public List<Integer> getDeliveries(int deliveryManId) {
        return null;
    }

    @Override
    public String validateDelivery(int commandId, int deliveryManId, String addressClient) {
        return null;
    }
}
