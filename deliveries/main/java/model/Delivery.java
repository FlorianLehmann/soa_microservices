package model;

import java.io.Serializable;

public class Delivery {
    private int deliveryManId;
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
