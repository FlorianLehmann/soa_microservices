package bean;

import entity.Order;
import microservices.OrdersRESTImpl;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Stateless
public class OrderRegister implements Register {
    private final static Logger LOGGER = Logger.getLogger(OrderRegister.class.getName());
    private static final Properties producerProps = new Properties();
    private static final Pattern phonePattern = Pattern.compile("^(?:(?:\\(?\\d\\)?\\s*-*){10}|\\+(?:\\(?\\d\\)?\\s*-*){11})$");
    private static final Pattern locationPattern = Pattern.compile("^\\d{1,2}.\\d{1,7},\\d{1,2}.\\d{1,7}$");
    private final Producer<String, String> producer = new KafkaProducer<>(producerProps, new StringSerializer(), new StringSerializer());

    @PersistenceContext(unitName = "orders")
    private EntityManager entityManager;

    public OrderRegister() {
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
    public void newOrder(String name, String restaurant, String product, String customerLocation, String restaurantLocation, String phone) {
        Matcher matcher = phonePattern.matcher(phone);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Phone number is not correct : \"" + phone + '\"');
        }
        matcher = locationPattern.matcher(customerLocation);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Location is not correct : \"" + customerLocation + '\"');
        }
        matcher = locationPattern.matcher(restaurantLocation);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Location is not correct : \"" + restaurantLocation + '\"');
        }
        LOGGER.info("Creating new order: '" + name + "' ordered a '" + product + "' at '" + restaurant + "'");
        producer.send(new ProducerRecord<>("order", new JSONObject()
                .put("event", "new_order")
                .put("data", new JSONObject()
                        .put("name", name)
                        .put("restaurant", restaurant)
                        .put("product", product)
                        .put("customerLocation", customerLocation)
                        .put("restaurantLocation", restaurantLocation)
                        .put("phone", phone)).toString()));
    }

    @Override
    public void saveOrder(Order order) {
        LOGGER.info("Saving " + order + " in database");
        entityManager.persist(order);
    }

    @Override
    public List<Order> listOrders(String restaurant) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root).where(criteriaBuilder.equal(root.get("restaurant"), restaurant));
        return entityManager.createQuery(query).getResultList();
    }
}
