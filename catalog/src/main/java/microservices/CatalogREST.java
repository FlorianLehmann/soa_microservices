package microservices;

import model.Meal;
import model.Review;

import javax.ejb.Local;
import javax.ws.rs.*;
import java.util.List;

@Local
@Produces({"application/json"})
public interface CatalogREST {

    @Path("/meals")
    @GET
    List<Meal> listMeals();

    @Path("/meals")
    @POST
    void createMeal(String request);


    @Path("/restaurants")
    @GET
    List<String> listRestaurant();


    @Path("/categories")
    @GET
    List<String> listCategories();

    @Path("/categories/{categoryName}/meals")
    @GET
    List<Meal> listCategoryProducts(@PathParam("categoryName") String categoryName);

    @Path("/restaurants/{restaurantName}/meals")
    @GET
    List<Meal> listRestaurantProducts(@PathParam("restaurantName") String restaurantName);

    @Path("/reviews")
    @GET
    List<Review> listReviews();


    @Path("/reviews/restaurants/{restaurantName}")
    @GET
    List<Review> getRestaurantReview(@PathParam("restaurantName") String restaurantName);

    @Path("/reviews/restaurants/{restaurantName}/meals/{mealName}")
    @GET
    List<Review> getMealReview(@PathParam("restaurantName") String restaurantName, @PathParam("mealName") String mealName);

    @Path("/reviews")
    @POST
    void createReview(String message);
}

