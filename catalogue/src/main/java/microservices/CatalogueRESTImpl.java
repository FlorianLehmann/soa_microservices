package microservices;

import model.Product;

import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.Collection;

@Path("rest/catalogue")
public class CatalogueRESTImpl implements CatalogueREST {
    @Override
    public Collection<Product> listProducts() {
        return new ArrayList<>();
    }
}
