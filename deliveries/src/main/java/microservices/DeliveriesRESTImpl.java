package microservices;

import model.Delivery;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/rest/deliveries")
public class DeliveriesRESTImpl implements DeliveriesREST {

    @PersistenceContext(unitName="deliveries")
    private EntityManager entityManager;

    @Override
    public List<Delivery> getDeliveries(int deliveryManId) {
        ArrayList a = new ArrayList<>();
        a.add(new Delivery(0, 1 , "adresse"));
        return a;
    }

    @Override
    public String validateDelivery(int commandId, int deliveryManId, String addressClient) {
        return null;
    }

    @Override
    public void addDelivery(String addressClient) {
        Delivery delivery = new Delivery(0, 0, addressClient);
        entityManager.persist(delivery);
    }
}
