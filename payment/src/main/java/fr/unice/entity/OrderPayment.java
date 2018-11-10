package fr.unice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class OrderPayment {

    @Id
    private int orderId;
    private boolean paid;

    public OrderPayment(int orderId) {
        this.orderId = orderId;
        this.paid = false;
    }

    public OrderPayment() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderPayment orderPayment = (OrderPayment) o;
        return orderId == orderPayment.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}
