package model;

import javax.persistence.*;

@Entity
@Table(name="meals")
public class Meal {

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
