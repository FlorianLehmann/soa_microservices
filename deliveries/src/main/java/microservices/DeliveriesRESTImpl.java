package microservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Address;
import model.Delivery;
import model.DeliveryMan;
import model.DistanceDelivery;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Stateless
@Path("/rest/deliveries")
public class DeliveriesRESTImpl implements DeliveriesREST {

    @PersistenceContext/*(unitName="deliveries")*/
    private EntityManager entityManager;

    // JSON unordered
    public List<Delivery> getUnassignedDeliveries() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Delivery> criteria = builder.createQuery(Delivery.class);
        Root<Delivery> root = criteria.from(Delivery.class);

        criteria.select(root).where(builder.equal(root.get("deliveryMan"), null));
        TypedQuery<Delivery> query = entityManager.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<Delivery> getDeliveries() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Delivery> criteria = builder.createQuery(Delivery.class);
        Root<Delivery> root = criteria.from(Delivery.class);

        criteria.select(root);
        TypedQuery<Delivery> query = entityManager.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public void addDelivery(String request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Delivery delivery = objectMapper.readValue(request, Delivery.class);

            entityManager.persist(delivery);
            System.out.println(delivery.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DistanceDelivery> getUnassignedDeliveriesNearBy(int deliveryManLongitude, int deliveryManLatitude) {
        List<Delivery> deliveries = new ArrayList<>(getUnassignedDeliveries());
        deliveries.sort(new Comparator<Delivery>() {
            @Override
            public int compare(Delivery o1, Delivery o2) {
                double distanceFirstWay = o1.getRestaurantAdress().distanceInMTo(new Address(deliveryManLatitude, deliveryManLongitude))
                        + o1.getRestaurantAdress().distanceInMTo(o1.getCustomerAddress());
                double distanceSecondWay = o2.getRestaurantAdress().distanceInMTo(new Address(deliveryManLatitude, deliveryManLongitude))
                        + o2.getRestaurantAdress().distanceInMTo(o2.getCustomerAddress());

                return distanceFirstWay < distanceSecondWay ? -1 : 1;
            }
        });

        List<DistanceDelivery> distanceDeliveries = new ArrayList<DistanceDelivery>();
        for (Delivery delivery : deliveries) {
            distanceDeliveries.add(new DistanceDelivery(delivery.getId(), delivery.getRestaurantAdress().distanceInMTo(new Address(deliveryManLatitude, deliveryManLongitude)) + delivery.getRestaurantAdress().distanceInMTo(delivery.getCustomerAddress())));
        }
        return distanceDeliveries;
    }

    @Override
    public void updateDeliveryState(long deliveriesId, String deliveryState) {

    }

}
