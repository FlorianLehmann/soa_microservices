package microservices;

import model.Product;

import javax.ws.rs.*;
import java.util.Collection;

@Produces({"application/json"})
public interface CatalogueREST {

    @Path("/products")
    @GET
    Collection<Product> listProducts();
}

