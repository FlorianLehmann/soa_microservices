package microservices;

import entity.Order;

import javax.ws.rs.*;
import java.util.List;

@Produces({"application/json"})
public interface OrdersREST {

    @Path("/neworder")
    @POST
    public void addOrder(@QueryParam("productId") int productId, @QueryParam("restaurantId") int restaurantId);

    @Path("/restaurants/{restaurantId}/orders")
    @GET
    public List<Order> getOrders(@PathParam("restaurantId") int restaurantId);

}
