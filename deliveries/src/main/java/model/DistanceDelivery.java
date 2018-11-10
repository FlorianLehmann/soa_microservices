package model;

public class DistanceDelivery {

    private int id;
    private double distance;

    public DistanceDelivery(int id, double distance) {
        this.id = id;
        this.distance = distance;
    }

    public DistanceDelivery() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
