package microservices;

import model.Meal;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;
import java.util.List;

@Stateless
@Path("rest/catalog")
public class CatalogRESTImpl implements CatalogREST {

    @PersistenceContext(unitName="catalog")
    private EntityManager entityManager;

    @Override
    public List<Meal> listMeals() {
        return entityManager.createQuery("select m from Meal m").getResultList();
    }

    @Override
    public List<String> listRestaurant() {
        return entityManager.createQuery("select distinct m.restaurant from Meal m").getResultList();
    }

    @Override
    public List<String> listCategories() {
        return entityManager.createQuery("select distinct m.category from Meal m").getResultList();
    }

    @Override
    public List<Meal> listCategoryProducts(String categoryName) {
        return entityManager.createQuery("select m from Meal m where m.category = :categoryName")
                .setParameter("categoryName", categoryName)
                .getResultList();
    }

    @Override
    public List<String> listRestaurantProducts(String restaurantName) {
        return entityManager.createQuery("select m from Meal m where m.restaurant = :restaurantName")
                .setParameter("restaurantName", restaurantName)
                .getResultList();
    }

}
