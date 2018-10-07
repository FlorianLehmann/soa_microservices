package model;

public class Delivery {

    private String addressClient;

    public Delivery(String addressClient) {
        this.addressClient = addressClient;
    }

    public Delivery() {

    }

    public String getAddressClient() {
        return addressClient;
    }

    public void setAddressClient(String addressClient) {
        this.addressClient = addressClient;
    }

}