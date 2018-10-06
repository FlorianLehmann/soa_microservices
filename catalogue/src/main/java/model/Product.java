package model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="products")
public class Product {

    private int id;
    private String name;

    public Product() {
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
