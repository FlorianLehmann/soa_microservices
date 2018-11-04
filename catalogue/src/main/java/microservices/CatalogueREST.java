package microservices;

import model.Meal;

import javax.ejb.Local;
import javax.ws.rs.*;
import java.util.List;

@Local
@Produces({"application/json"})
public interface CatalogueREST {

    @Path("/meals")
    @GET
    List<Meal> listMeals();


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
    List<String> listRestaurantProducts(@PathParam("restaurantName") String restaurantName);

}

