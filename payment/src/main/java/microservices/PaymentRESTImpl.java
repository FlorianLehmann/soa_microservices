package microservices;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class PaymentRESTImpl implements PaymentREST {

    private final Producer<String, String> producer = new KafkaProducer<>(producerProps, new StringSerializer(), new StringSerializer());
    private static final Properties producerProps = new Properties();

    static {
        producerProps.put("bootstrap.servers", "kafka-bus:9092");
        producerProps.put("acks", "all");
        producerProps.put("retries", 0);
        producerProps.put("batch.size", 16384);
        producerProps.put("linger.ms", 1);
        producerProps.put("buffer.memory", 33554432);
    }

    public PaymentRESTImpl() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            producer.close();
        }));
    }

    @Override
    public void payment(String message) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(message);
        } catch (org.json.simple.parser.ParseException e) {
            producer.send(new ProducerRecord<String, String>("payment", "\"commandN\": \"not payed\""));
        }
        String cardNumber = json.getString("cardNumber");
        String expirationDate = json.getString("expirationDate");
        String cardSecurityCode = json.getString("cardSecurityCode");
        String commandN = json.getString("commandN");

        if (cardNumber.length() == 16) {
            if (cardSecurityCode.length() == 3) {
                int[] ints = new int[cardSecurityCode.length()];
                for (int i = 0; i < cardSecurityCode.length(); i++) {
                    try {
                        ints[i] = Integer.parseInt(cardSecurityCode.substring(i, i + 1));
                    } catch (NumberFormatException e) {
                        producer.send(new ProducerRecord<String, String>("payment", "\"commandN\": \"not payed\""));
                    }
                }
                String day = expirationDate.split("-")[0], month = expirationDate.split("-")[1];
                if ( day.length() == 2 && month.length() == 2) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
                    simpleDateFormat.setLenient(false);
                    Date expiry = simpleDateFormat.parse(day+'/'+month);
                    if (expiry.before(new Date())) {
                        producer.send(new ProducerRecord<String, String>("payment", "\"commandN\": \"not payed\""));
                    }
                } else {
                    producer.send(new ProducerRecord<String, String>("payment", "\"commandN\": \"not payed\""));
                }
                producer.send(new ProducerRecord<String, String>("payment", "\"commandN\": \"payed\""));
                if (validateCreditCardNumber(cardNumber)){
                    producer.send(new ProducerRecord<String, String>("payment", "\"commandN\": \"payed\""));
                } else {
                    producer.send(new ProducerRecord<String, String>("payment", "\"commandN\": \"not payed\""));
                }
            } else {
                producer.send(new ProducerRecord<String, String>("payment", "\"commandN\": \"not payed\""));
            }

        } else {
            producer.send(new ProducerRecord<String, String>("payment", "\"commandN\": \"not payed\""));
        }
    }
    /*source https://www.journaldev.com/1443/java-credit-card-validation-luhn-algorithm-java*/
    /*Luhn Algorithm in Java*/
    private static boolean validateCreditCardNumber(String str) {

        int[] ints = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            try {
                ints[i] = Integer.parseInt(str.substring(i, i + 1));
            } catch (NumberFormatException e) {
                return false;
            }
        }
        for (int i = ints.length - 2; i >= 0; i = i - 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        int sum = 0;
        for (int i = 0; i < ints.length; i++) {
            sum += ints[i];
        }
        if (sum % 10 == 0) {
            return true;
        } else {
            return false;
        }
    }










}
