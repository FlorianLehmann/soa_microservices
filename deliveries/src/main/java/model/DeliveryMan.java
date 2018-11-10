package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class DeliveryMan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public DeliveryMan() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryMan that = (DeliveryMan) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
