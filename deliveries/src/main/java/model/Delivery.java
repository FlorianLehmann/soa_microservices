package model;

import java.io.Serializable;

@Entity
@Table(name="deliveries")
public class Delivery {
    private int deliveryManId;
    @Id
    private int commandId;
    private String addressClient;

    public Delivery(int deliveryManId, int commandId) {
        this.deliveryManId = deliveryManId;
        this.commandId = commandId;
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
