package microservices;

import entity.Order;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;
import java.util.List;

@Stateless
@Path("/rest/orders")
public class OrdersRESTImpl implements OrdersREST {

    @PersistenceContext(unitName="orders")
    private EntityManager entityManager;

    @Override
    public void addOrder(int productId, int restaurantId) {
        entityManager.persist(new Order(productId, restaurantId));
    }

    @Override
    public List<Order> getOrders(int restaurantId) {
        return entityManager.createQuery("select o from Order o").getResultList();
    }
}
