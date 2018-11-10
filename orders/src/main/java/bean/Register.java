package bean;

import entity.Order;

import javax.ejb.Local;
import java.util.List;

@Local
public interface Register {
    void newOrder(String name, String restaurant, String product, String customerLocation, String restaurantLocation, String phone);

    void saveOrder(Order order);

    List<Order> listOrders(String restaurant);
}
