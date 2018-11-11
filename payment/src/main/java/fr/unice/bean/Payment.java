package fr.unice.bean;

import fr.unice.entity.OrderPayment;
import fr.unice.model.CreditCard;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Properties;
import java.util.logging.Logger;

@Stateless
public class Payment implements Pay {

    private final static Logger LOGGER = Logger.getLogger(Payment.class.getName());
    private static final Properties producerProps = new Properties();
    private final Producer<String, String> producer = new KafkaProducer<>(producerProps, new StringSerializer(), new StringSerializer());


    @PersistenceContext
    private EntityManager entityManager;

    public Payment() {
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
    public void payOrder(CreditCard creditCard, int orderId) {
        OrderPayment orderPayment = entityManager.merge(new OrderPayment(orderId));
        orderPayment.setPaid(true);
        entityManager.merge(orderPayment);
        producer.send(new ProducerRecord<>("payment", new JSONObject()
                .put("event", "paid_order")
                .put("data", new JSONObject()
                        .put("orderId", orderPayment.getOrderId())).toString()));
        LOGGER.info("orderPayment : " + orderPayment.getOrderId() + " is paid.");
    }

    @Override
    public void addOrder(OrderPayment orderPayment) {
        entityManager.persist(orderPayment);
        LOGGER.info("Create new orderPayment : " + orderPayment.getOrderId() + " and he has to be paid.");
    }

}
