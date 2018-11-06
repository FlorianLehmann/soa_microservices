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
    private String location;
    private String phone;

    public Order(String name, String restaurant, String product, String location, String phone) {
        this.name = name;
        this.restaurant = restaurant;
        this.product = product;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Order{orderId=" + orderId + ", name='" + name + "\', restaurant='" + restaurant + "\', product='" + product + "\', location='" + location + "\', phone='" + phone + "\'}";
    }
}
