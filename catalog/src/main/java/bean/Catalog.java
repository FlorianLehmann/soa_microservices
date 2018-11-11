package bean;

import model.Meal;
import model.Review;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface Catalog {
    List<Meal> listMeal();

    Optional<Meal> findMeal(String restaurant, String name);

    void createMeal(Meal meal);

    List<String> listRestaurant();

    List<String> listCatagories();

    List<Meal> listCatageryProducts(String categoryName);

    List<Meal> listRestaurantProducts(String restaurantName);

    List<Review> getRestaurantReview(String restaurantName);

    void createReview(Review review);

    List<Review> getMealReview(String restaurantName, String mealName);
}
