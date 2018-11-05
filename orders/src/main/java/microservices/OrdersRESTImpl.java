package microservices;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

@Stateless
@Path("/")
public class OrdersRESTImpl implements OrdersREST {
    private static final Properties consumerProps = new Properties();
    private static final Properties producerProps = new Properties();
    private final Producer<String, String> producer = new KafkaProducer<>(producerProps, new StringSerializer(), new StringSerializer());
    private final Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps, new StringDeserializer(), new StringDeserializer());

    public OrdersRESTImpl() {
        consumer.subscribe(Collections.singletonList("new-order"));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {producer.close(); consumer.close();}));
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                addOrder();
            }
        }, 1000, 1000);
    }

    @PersistenceContext(unitName = "orders")
    private EntityManager entityManager;

    @Override
    public Response newOrder(String message) {
        JSONObject json = new JSONObject(message);
        System.out.println("New Order : " + json.toString());
        producer.send(new ProducerRecord<>("new-order", json.toString()));
        return Response.ok().build();
    }

    @Schedule(second = "1")
    public void addOrder() {
        ConsumerRecords<String, String> records = consumer.poll(1000);
        for (ConsumerRecord<String, String> record : records) {
            System.out.println("Save Order : " + record.toString());
        }

    }


    @Override
    public Response getOrders(int restaurantId) {
        return Response.ok(entityManager.createQuery("select o from Order o").getResultList()).build();
    }

    static {
        producerProps.put("bootstrap.servers", "kafka-bus:9092");
        producerProps.put("acks", "all");
        producerProps.put("retries", 0);
        producerProps.put("batch.size", 16384);
        producerProps.put("linger.ms", 1);
        producerProps.put("buffer.memory", 33554432);

        consumerProps.put("bootstrap.servers", "kafka-bus:9092");
        consumerProps.put("group.id", "uberoo");
        consumerProps.put("enable.auto.commit", "true");
        consumerProps.put("auto.commit.interval.ms", "1000");
    }
}
