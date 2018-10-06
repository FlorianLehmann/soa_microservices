package microservices;

import model.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.Collection;

@Path("rest/catalogue")
public class CatalogueRESTImpl implements CatalogueREST {

    @PersistenceContext(unitName="catalogue")
    private EntityManager entityManager;

    @Override
    public Collection<Product> listProducts() {
        return entityManager.createQuery("select p from Product p").getResultList();
    }
}
