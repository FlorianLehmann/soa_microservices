package fr.unice.kafka;

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

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Collections;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

@Singleton
@Startup
public class ConsumeMessage {

    private static final Properties consumerProps = new Properties();
    private static final Properties producerProps = new Properties();
    private final Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps, new StringDeserializer(), new StringDeserializer());
    private final Producer<String, String> producer = new KafkaProducer<>(producerProps, new StringSerializer(), new StringSerializer());

    public ConsumeMessage() {
        consumer.subscribe(Collections.singletonList("order"));
        Runtime.getRuntime().addShutdownHook(new Thread(consumer::close));
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                consume();
            }
        }, 1000, 1000);
    }

    private void consume() {
        ConsumerRecords<String, String> records = consumer.poll(1000);
        for (ConsumerRecord<String, String> record : records) {
            JSONObject jsonObject = new JSONObject(record.value());
            processRecord(jsonObject);
        }
    }

    private void processRecord(JSONObject record) {
        System.out.println("MESSAGE KITCHEN : " + record);

        switch (record.getString("event")) {
            case "new_order":
                JSONObject data = record.getJSONObject("data");

                producer.send(new ProducerRecord<>("kitchen", new JSONObject()
                        .put("event", "ready_to_be_delivered")
                        .put("data", new JSONObject()
                        .put("customerName", data.getString("name"))
                        .put("customerLocation", data.getString("customerLocation"))
                        .put("restaurantLocation", data.getString("restaurantLocation"))).toString()));
                break;
        }
    }

    static {
        consumerProps.put("bootstrap.servers", "kafka-bus:9092");
        consumerProps.put("group.id", "uberookit");
        consumerProps.put("enable.auto.commit", "true");
        consumerProps.put("auto.commit.interval.ms", "1000");
        consumerProps.put("auto.offset.reset", "earliest");
        producerProps.put("bootstrap.servers", "kafka-bus:9092");
        producerProps.put("acks", "all");
        producerProps.put("retries", 0);
        producerProps.put("batch.size", 16384);
        producerProps.put("linger.ms", 1);
        producerProps.put("buffer.memory", 33554432);
    }
}
