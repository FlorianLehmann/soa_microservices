package microservices;

import model.ETA;

import javax.ws.rs.*;

@Produces({"application/json"})
public interface ValidationREST {

    @Path("/products/{productId}/eta/{address}")
    @GET
    public ETA getETA(@PathParam("productId") int productId, @PathParam("address") String address);

    @Path("/validate/product")
    @POST
    public void validateProduct( @QueryParam("productId") int productId, @QueryParam("name") String customerName, @QueryParam("address") String customerAddress );

}

