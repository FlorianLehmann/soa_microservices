package fr.unice.bean;

import fr.unice.entity.OrderPayment;
import fr.unice.model.CreditCard;

import javax.ejb.Local;

@Local
public interface Pay {

    public void payOrder(CreditCard creditCard, int orderId);
    public void addOrder(OrderPayment orderPayment);

}
