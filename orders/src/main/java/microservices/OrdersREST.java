package microservices;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface OrdersREST {

    @POST
    @Path("/new_order")
    @Produces(MediaType.APPLICATION_JSON)
    Response newOrder(String message);

    @GET
    @Path("/restaurants/{restaurantId}/orders")
    @Produces(MediaType.APPLICATION_JSON)
    Response getOrders(@PathParam("restaurantId") int restaurantId);
}
