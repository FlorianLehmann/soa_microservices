package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Meal meal;
    private String reviewer;
    private String feedback;

    public Review() {}

    public Review(Meal meal, String feedback, String reviewer) {
        this.meal = meal;
        this.feedback = feedback;
        this.reviewer = reviewer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }
}
