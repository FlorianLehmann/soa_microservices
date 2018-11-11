package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Meal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String category;
    private String restaurant;
    private String name;

    public Meal(String category, String restaurant, String name) {
        this.category = category;
        this.restaurant = restaurant;
        this.name = name;
    }

    public Meal() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getName() {
        return name;
    }
}
