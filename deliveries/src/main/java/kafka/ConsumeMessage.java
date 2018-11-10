package kafka;

import bean.DeliveryRegister;
import model.Address;
import model.Delivery;
import model.DeliveryMan;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Collections;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

@Singleton
@Startup
public class ConsumeMessage {

    private static final Properties consumerProps = new Properties();

    private final Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps, new StringDeserializer(), new StringDeserializer());

    @EJB
    private DeliveryRegister deliveryRegister;

    public ConsumeMessage() {
        consumer.subscribe(Collections.singletonList("kitchen"));
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
        System.out.println("MESSAGE DELIEVRY : " + record);

        switch (record.getString("event")) {
            case "ready_to_be_delivered":
                JSONObject data = record.getJSONObject("data");

                String []customerCoordinate = data.getString("customerLocation").split(",");
                String []restaurantCoordinate = data.getString("restaurantLocation").split(",");


                Address customerAddress = new Address(Double.parseDouble(customerCoordinate[0]), Double.parseDouble(customerCoordinate[1]));
                Address restaurantAddress = new Address(Double.parseDouble(restaurantCoordinate[0]), Double.parseDouble(restaurantCoordinate[1]));


                deliveryRegister.addDelivery(new Delivery(restaurantAddress, customerAddress, data.getString("customerName"), null));

                break;
        }
    }

    static {
        consumerProps.put("bootstrap.servers", "kafka-bus:9092");
        consumerProps.put("group.id", "deliveries");
        consumerProps.put("enable.auto.commit", "true");
        consumerProps.put("auto.commit.interval.ms", "1000");
        consumerProps.put("auto.offset.reset", "earliest");
    }
}
