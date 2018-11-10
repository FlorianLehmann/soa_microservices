package model;

import javax.persistence.*;
import java.awt.geom.Point2D;

@Embeddable
public class Address {

    private double latitude;
    private double longitude;

    public Address() {
    }

    public Address(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double distanceInMTo(Address address) {
        return Point2D.distance(latitude, longitude, address.latitude, address.longitude);
    }

}
