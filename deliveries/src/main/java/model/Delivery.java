package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="deliveries")
public class Delivery {
    private int deliveryManId;
    @Id
    private int commandId;
    private String addressClient;

    public Delivery(int deliveryManId, int commandId, String addressClient) {
        this.deliveryManId = deliveryManId;
        this.commandId = commandId;
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
}