package microservices;

import javax.ws.rs.*;

@Produces({"text/plain"})
public interface ValidationREST {

    @Path("/products/{productId}/eta/{address}")
    @GET
    public String getETA( @PathParam("productId") int productId, @PathParam("address") String address);

    @Path("/validate/product")
    @POST
    public String validateProduct( @QueryParam("productId") int productId, @QueryParam("name") String customerName, @QueryParam("address") String customerAddress );

}

