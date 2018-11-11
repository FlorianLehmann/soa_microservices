package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
/*@Table(name="deliveries")*/
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="longitude", column = @Column(name = "restaurant_longitude")),
            @AttributeOverride(name="latitude", column = @Column(name = "restaurant_latitude"))
    })
    private Address restaurantAdress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="longitude", column = @Column(name = "customer_longitude")),
            @AttributeOverride(name="latitude", column = @Column(name = "customer_latitude"))
    })
    private Address customerAddress;
    private String customerName;


    @OneToOne
    private DeliveryMan deliveryMan;

    public Delivery() {
    }

    public Delivery(Address restaurantAdress, Address customerAddress, String customerName, DeliveryMan deliveryMan) {
        this.restaurantAdress = restaurantAdress;
        this.customerAddress = customerAddress;
        this.deliveryMan = deliveryMan;
        this.customerName = customerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getRestaurantAdress() {
        return restaurantAdress;
    }

    public void setRestaurantAdress(Address restaurantAdress) {
        this.restaurantAdress = restaurantAdress;
    }

    public Address getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(Address customerAddress) {
        this.customerAddress = customerAddress;
    }

    public DeliveryMan getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(DeliveryMan deliveryMan) {
        this.deliveryMan = deliveryMan;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return id == delivery.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
