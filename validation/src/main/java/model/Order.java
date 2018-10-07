package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class Order {

    private int productId;
    private int restaurantId;

    public Order(int productId, int restaurantId) {
        this.productId = productId;
        this.restaurantId = restaurantId;
    }

    public Order() {}

    public int getProductId() {
        return productId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
