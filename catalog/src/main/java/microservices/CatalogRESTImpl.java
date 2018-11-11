package microservices;

import bean.Catalog;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Meal;
import model.Review;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
@Path("rest/catalog")
public class CatalogRESTImpl implements CatalogREST {

    @PersistenceContext(unitName = "catalog")
    private EntityManager entityManager;

    @EJB
    private Catalog catalog;

    @Override
    public List<Meal> listMeals() {
        return catalog.listMeal();
    }

    @Override
    public void createMeal(String request) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Meal meal = objectMapper.readValue(request, Meal.class);
            catalog.createMeal(meal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> listRestaurant() {
        return catalog.listRestaurant();
    }

    @Override
    public List<String> listCategories() {
        return catalog.listCatagories();
    }

    @Override
    public List<Meal> listCategoryProducts(String categoryName) {
        return catalog.listCatageryProducts(categoryName);
    }

    @Override
    public List<Meal> listRestaurantProducts(String restaurantName) {
        return catalog.listRestaurantProducts(restaurantName);
    }

    @Override
    public List<Review> getRestaurantReview(String restaurantName) {
        return catalog.getRestaurantReview(restaurantName);
    }

    @Override
    public List<Review> getMealReview(String restaurantName, String mealName) {
        return catalog.getMealReview(restaurantName, mealName);
    }


    @Override
    public List<Review> listReviews() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> query = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void createReview(String request) {
        JSONObject jsonObject = new JSONObject(request);
        String restaurant = jsonObject.getString("restaurant");
        String mealName = jsonObject.getString("meal");
        Optional<Meal> meal = catalog.findMeal(restaurant, mealName);
        if (meal.isPresent()) {
            String reviewer = jsonObject.getString("reviewer");
            String feedback = jsonObject.getString("feedback");
            catalog.createReview(new Review(meal.get(), feedback, reviewer));
        }
    }
}
