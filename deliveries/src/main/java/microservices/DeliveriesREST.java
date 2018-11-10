package microservices;


import model.Delivery;
import model.DistanceDelivery;

import javax.ws.rs.*;
import java.util.List;

@Produces({"application/json"})
public interface DeliveriesREST {


    @Path("/deliveries")
    @GET
    public List<Delivery> getDeliveries();


    @Path("/deliveries")
    @POST
    public void addDelivery(String request);


    @Path("/deliveries/{longitude}/{latitude}")
    @GET
    public List<DistanceDelivery> getUnassignedDeliveriesNearBy(@PathParam("longitude") int deliveryManLongitude,
                                                                @PathParam("latitude") int deliveryManLatitude);

    @Path("/deliveries/{id}/{state}")
    @PUT
    public void updateDeliveryState(@PathParam("id") long deliveriesId,
                                    @PathParam("state") String deliveryState);



}

