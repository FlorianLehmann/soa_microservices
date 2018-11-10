package bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Delivery;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.List;

@Stateless
public class DeliveryRegistry implements DeliveryRegister {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
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
    public void addDelivery(Delivery delivery) {
        entityManager.persist(delivery);
    }

    @Override
    public void updateDeliveryState(int deliveriesId, String deliveryState) {

    }
}
