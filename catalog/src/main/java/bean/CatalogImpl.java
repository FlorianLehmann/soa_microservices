package bean;

import model.Meal;
import model.Review;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Stateless
public class CatalogImpl implements Catalog {
    private final static Logger LOGGER = Logger.getLogger(CatalogImpl.class.getName());
    private static final Properties producerProps = new Properties();
    private final Producer<String, String> producer = new KafkaProducer<>(producerProps, new StringSerializer(), new StringSerializer());

    @PersistenceContext(unitName = "catalog")
    private EntityManager entityManager;

    public CatalogImpl() {
        Runtime.getRuntime().addShutdownHook(new Thread(producer::close));
    }

    @Override
    public List<Meal> listMeal() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Meal> query = criteriaBuilder.createQuery(Meal.class);
        Root<Meal> root = query.from(Meal.class);
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void createMeal(Meal meal) {
        Optional<Meal> found = findMeal(meal.getRestaurant(), meal.getName());
        if (!found.isPresent()) {
            JSONObject json = new JSONObject()
                    .put("event", "add_meal")
                    .put("data", new JSONObject()
                            .put("category", meal.getCategory())
                            .put("name", meal.getName())
                            .put("restaurant", meal.getRestaurant()));
            producer.send(new ProducerRecord<>("catalog", json.toString()));
        }
    }

    @Override
    public void saveMeal(Meal meal) {
        entityManager.persist(meal);
    }

    @Override
    public List<String> listRestaurant() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Meal> query = criteriaBuilder.createQuery(Meal.class);
        Root<Meal> root = query.from(Meal.class);
        query.select(root);
        return entityManager.createQuery(query).getResultList().stream().map(Meal::getRestaurant).collect(Collectors.toList());
    }

    @Override
    public List<String> listCatagories() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Meal> query = criteriaBuilder.createQuery(Meal.class);
        Root<Meal> root = query.from(Meal.class);
        query.select(root);
        return new ArrayList<>(entityManager.createQuery(query).getResultList().stream().map(Meal::getCategory).collect(Collectors.toSet()));
    }

    @Override
    public List<Meal> listCatageryProducts(String categoryName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Meal> query = criteriaBuilder.createQuery(Meal.class);
        Root<Meal> root = query.from(Meal.class);
        query.select(root).where(criteriaBuilder.equal(root.get("category"), categoryName));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Meal> listRestaurantProducts(String restaurantName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Meal> query = criteriaBuilder.createQuery(Meal.class);
        Root<Meal> root = query.from(Meal.class);
        query.select(root).where(criteriaBuilder.equal(root.get("restaurant"), restaurantName));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Review> getRestaurantReview(String restaurantName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> query = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root).where(criteriaBuilder.equal(root.get("meal").get("restaurant"), restaurantName));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Review> getMealReview(String restaurantName, String mealName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> query = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root).where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("meal").get("restaurant"), restaurantName),
                criteriaBuilder.equal(root.get("meal").get("name"), mealName)));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Optional<Meal> findMeal(String restaurant, String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Meal> query = criteriaBuilder.createQuery(Meal.class);
        Root<Meal> root = query.from(Meal.class);
        query.select(root);
        List<Meal> resultList = entityManager.createQuery(query).getResultList();
        if (resultList.size() == 1) {
            return Optional.of(resultList.get(0));
        }
        return Optional.empty();
    }

    @Override
    public void createReview(Review review) {
        producer.send(new ProducerRecord<>("catalog", new JSONObject()
                .put("event", "new_review")
                .put("data", new JSONObject()
                        .put("mealName", review.getMeal().getName())
                        .put("mealRestaurant", review.getMeal().getRestaurant())
                        .put("reviewer", review.getReviewer())
                        .put("feedback", review.getFeedback())).toString()));
    }

    @Override
    public void saveReview(Review review) {
        entityManager.persist(review);
    }

    static {
        producerProps.put("bootstrap.servers", "kafka-bus:9092");
        producerProps.put("acks", "all");
        producerProps.put("retries", 0);
        producerProps.put("batch.size", 16384);
        producerProps.put("linger.ms", 1);
        producerProps.put("buffer.memory", 33554432);
    }
}
