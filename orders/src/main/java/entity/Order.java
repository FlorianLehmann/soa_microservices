package entity;

import javax.persistence.*;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;

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

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

}
