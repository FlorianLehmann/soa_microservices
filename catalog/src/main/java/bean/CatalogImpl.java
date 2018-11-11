package bean;

import model.Meal;
import model.Review;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class CatalogImpl implements Catalog {
    @PersistenceContext(unitName = "catalog")
    private EntityManager entityManager;

    @Override
    public List<Meal> listMeal() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Meal> query = criteriaBuilder.createQuery(Meal.class);
        Root<Meal> root = query.from(Meal.class);
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void createMeal(Meal meal) {
        entityManager.persist(meal);
    }

    @Override
    public List<String> listRestaurant() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Meal> query = criteriaBuilder.createQuery(Meal.class);
        Root<Meal> root = query.from(Meal.class);
        query.select(root);
        return entityManager.createQuery(query).getResultList().stream().map(Meal::getRestaurant).collect(Collectors.toList());
    }

    @Override
    public List<String> listCatagories() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Meal> query = criteriaBuilder.createQuery(Meal.class);
        Root<Meal> root = query.from(Meal.class);
        query.select(root);
        return new ArrayList<>(entityManager.createQuery(query).getResultList().stream().map(Meal::getCategory).collect(Collectors.toSet()));
    }

    @Override
    public List<Meal> listCatageryProducts(String categoryName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Meal> query = criteriaBuilder.createQuery(Meal.class);
        Root<Meal> root = query.from(Meal.class);
        query.select(root).where(criteriaBuilder.equal(root.get("category"), categoryName));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Meal> listRestaurantProducts(String restaurantName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Meal> query = criteriaBuilder.createQuery(Meal.class);
        Root<Meal> root = query.from(Meal.class);
        query.select(root).where(criteriaBuilder.equal(root.get("restaurant"), restaurantName));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Review> getRestaurantReview(String restaurantName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> query = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root).where(criteriaBuilder.equal(root.get("meal").get("restaurant"), restaurantName));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Review> getMealReview(String restaurantName, String mealName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> query = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.select(root).where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("meal").get("restaurant"), restaurantName),
                criteriaBuilder.equal(root.get("meal").get("name"), mealName)));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Optional<Meal> findMeal(String restaurant, String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Meal> query = criteriaBuilder.createQuery(Meal.class);
        Root<Meal> root = query.from(Meal.class);
        query.select(root);
        List<Meal> resultList = entityManager.createQuery(query).getResultList();
        if (resultList.size() == 1) {
            return Optional.of(resultList.get(0));
        }
        return Optional.empty();
    }

    @Override
    public void createReview(Review review) {
        entityManager.persist(review);
    }
}
