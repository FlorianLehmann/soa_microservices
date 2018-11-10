package bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Delivery;
import model.DeliveryMan;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

@Stateless
public class DeliveryRegistry implements DeliveryRegister {

    private final static Logger LOGGER = Logger.getLogger(DeliveryRegistry.class.getName());
    private static final Properties producerProps = new Properties();
    private final Producer<String, String> producer = new KafkaProducer<>(producerProps, new StringSerializer(), new StringSerializer());


    @PersistenceContext
    private EntityManager entityManager;

    public DeliveryRegistry() {
        Runtime.getRuntime().addShutdownHook(new Thread(producer::close));
    }


    static {
        producerProps.put("bootstrap.servers", "kafka-bus:9092");
        producerProps.put("acks", "all");
        producerProps.put("retries", 0);
        producerProps.put("batch.size", 16384);
        producerProps.put("linger.ms", 1);
        producerProps.put("buffer.memory", 33554432);
    }

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
    public void updateDeliveryState(int deliveryId, String deliveryState) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Delivery> criteria = builder.createQuery(Delivery.class);
        Root<Delivery> root = criteria.from(Delivery.class);

        criteria.select(root).where(builder.equal(root.get("id"), deliveryId));
        TypedQuery<Delivery> query = entityManager.createQuery(criteria);

        Delivery delivery = query.getSingleResult();
        producer.send(new ProducerRecord<>("delivery", new JSONObject()
                .put("event", "delivered")
                .put("data", new JSONObject()
                        .put("orderId", delivery.getId())).toString()));
    }

    @Override
    public void assignDeliveryTo(DeliveryMan deliveryMan) {

    }
}
