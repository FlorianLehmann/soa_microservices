package microservices;


import model.Delivery;

import javax.ws.rs.*;
import java.util.List;

@Produces({"application/json"})
public interface DeliveriesREST {


    // Get all deliveries of a specific delivery man
    @Path("/deliveryman/{id}")
    @GET
    public List<Delivery> /*list of CommandId*/ getDeliveries(@PathParam("id") int deliveryManId);

    // Validate a delivery
    @Path("/deliveredproduct")
    @PUT
    public String validateDelivery( @QueryParam("commandId") int commandId, @QueryParam("deliveryManId") int deliveryManId, @QueryParam("addressClient") String addressClient);


    //Add new delivery
    @Path("/adddelivery")
    @POST
    public void addDelivery(@QueryParam("addressClient") String addressClient);


    //Get all deliveries of a specified delivery man within a specified range
    @Path("/deliveryman/{id}/{longitude }/{latitude}")
    @GET
    public List<Delivery> GetDeliverriesNearBy(@PathParam("id") int deliveryManId ,
                                               @PathParam("longitude") long actualPositionLongitude ,
                                               @PathParam("latitude") long actualPositionLatitude);

    //Switch delivery state to either finished or terminated
    @Path("/deliverystate/{state}")
    @PUT
    public void switchDeliveryState(@PathParam("state") String deliveryState )



}

