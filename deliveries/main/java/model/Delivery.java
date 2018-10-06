package model;

import java.io.Serializable;

public class Delivery {
    private int deliveryManId;
    private int commandId;

    public Delivery(int deliveryManId, int commandId) {
        this.deliveryManId = deliveryManId;
        this.commandId = commandId;
    }

    public int getDeliveryManId() {
        return deliveryManId;
    }

    public void setDeliveryManId(int deliveryManId) {
        this.deliveryManId = deliveryManId;
    }
}
