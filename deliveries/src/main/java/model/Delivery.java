package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="deliveries")
public class Delivery {
    private String addressClient;
    private int deliveryManId;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int commandId;


    public Delivery(int deliveryManId, int commandId, String addressClient) {
        this.deliveryManId = deliveryManId;
        this.commandId = commandId;
        this.addressClient = addressClient;
    }

    public Delivery(int deliveryManId, String addressClient) {
        this.deliveryManId = deliveryManId;
        this.addressClient = addressClient;
    }

    public Delivery() {

    }

    public int getDeliveryManId() {
        return deliveryManId;
    }

    public int getCommandId() {
        return commandId;
    }

    public String getAddressClient() {
        return addressClient;
    }

    public void setAddressClient(String addressClient) {
        this.addressClient = addressClient;
    }
}
