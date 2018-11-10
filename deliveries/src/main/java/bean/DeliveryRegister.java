package bean;

import model.Delivery;
import model.DeliveryMan;

import javax.ejb.Local;
import java.util.List;

@Local
public interface DeliveryRegister {

    public List<Delivery> getUnassignedDeliveries();

    public List<Delivery> getDeliveries();

    public void addDelivery(Delivery delivery);

    public void updateDeliveryState(int deliveriesId, String deliveryState);

    public void assignDeliveryTo(DeliveryMan deliveryMan);

}
