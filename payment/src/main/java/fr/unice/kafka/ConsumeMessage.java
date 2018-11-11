package fr.unice.kafka;

import fr.unice.bean.Pay;
import fr.unice.entity.OrderPayment;
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

    private final static Logger LOGGER = Logger.getLogger(ConsumeMessage.class.getName());
    private static final Properties consumerProps = new Properties();
    private final Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps, new StringDeserializer(), new StringDeserializer());

    @EJB
    private Pay pay;

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
            LOGGER.info("consume record : " + record);
            JSONObject jsonObject = new JSONObject(record.value());
            processRecord(jsonObject);
        }
    }

    private void processRecord(JSONObject record) {
        System.out.println("MESSAGE ORDER : " + record);


        switch (record.getString("event")) {
            case "saving_order":
                JSONObject data = record.getJSONObject("data");
                pay.addOrder(new OrderPayment(data.getInt("orderId")));
                break;
        }
    }

    static {
        consumerProps.put("bootstrap.servers", "kafka-bus:9092");
        consumerProps.put("group.id", "payment");
        consumerProps.put("enable.auto.commit", "true");
        consumerProps.put("auto.commit.interval.ms", "1000");
        consumerProps.put("auto.offset.reset", "earliest");
    }
}
