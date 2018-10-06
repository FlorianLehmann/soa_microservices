package microservices;


import model.Delivery;

import javax.ws.rs.*;
import java.util.List;

@Produces({"application/json"})
public interface DeliveriesREST {

    @Path("/deliveryman/{id}")
    @GET
    public List<Integer> /*list of CommandId*/ getDeliveries(@PathParam("id") int deliveryManId);

    @Path("/deliveredproduct")
    @PUT
    public String validateDelivery( @QueryParam("commandId") int commandId, @QueryParam("deliveryManId") int deliveryManId);

}

