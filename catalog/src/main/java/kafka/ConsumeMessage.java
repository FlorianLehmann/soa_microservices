package kafka;

import bean.Catalog;
import model.Meal;
import model.Review;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.*;

@Singleton
@Startup
public class ConsumeMessage {

    private static final Properties consumerProps = new Properties();
    private final Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps, new StringDeserializer(), new StringDeserializer());

    @EJB
    private Catalog catalog;

    public ConsumeMessage() {
        consumer.subscribe(Collections.singletonList("catalog"));
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
        JSONObject data = record.getJSONObject("data");
        switch (record.getString("event")) {
            case "add_meal":
                String name = data.getString("name");
                String restaurant = data.getString("restaurant");
                String category = data.getString("category");
                catalog.saveMeal(new Meal(category, restaurant, name));
                break;
            case "new_review":
                String mealName = data.getString("mealName");
                String mealRestaurant = data.getString("mealRestaurant");
                String reviewer = data.getString("reviewer");
                String feedback = data.getString("feedback");
                Optional<Meal> meal = catalog.findMeal(mealRestaurant, mealName);
                meal.ifPresent(m -> catalog.saveReview(new Review(m, feedback, reviewer)));
                break;
        }
    }

    static {
        consumerProps.put("bootstrap.servers", "kafka-bus:9092");
        consumerProps.put("group.id", "catalog");
        consumerProps.put("enable.auto.commit", "true");
        consumerProps.put("auto.commit.interval.ms", "1000");
        consumerProps.put("auto.offset.reset", "earliest");
    }
}
