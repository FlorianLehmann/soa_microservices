package entity;

import javax.persistence.*;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;
    private String name;
    private String restaurant;
    private String product;
    private String customerLocation;
    private String restaurantLocation;
    private String phone;

    public Order(String name, String restaurant, String product, String customerLocation, String restaurantLocation, String phone) {
        this.name = name;
        this.restaurant = restaurant;
        this.product = product;
        this.customerLocation = customerLocation;
        this.restaurantLocation = restaurantLocation;
        this.phone = phone;
    }

    public Order() {}

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getCustomerLocation() {
        return customerLocation;
    }

    public void setCustomerLocation(String customerLocation) {
        this.customerLocation = customerLocation;
    }

    public String getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(String restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", name='" + name + '\'' +
                ", restaurant='" + restaurant + '\'' +
                ", product='" + product + '\'' +
                ", customerLocation='" + customerLocation + '\'' +
                ", restaurantLocation='" + restaurantLocation + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
