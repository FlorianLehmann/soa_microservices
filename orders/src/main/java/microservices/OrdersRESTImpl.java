package microservices;

import bean.Register;
import entity.Order;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Path("/")
public class OrdersRESTImpl implements OrdersREST {
    private final static Logger LOGGER = Logger.getLogger(OrdersRESTImpl.class.getName());
    @EJB
    private Register register;

    @Override
    public Response newOrder(String message) {
        try {
            JSONObject json = new JSONObject(message);
            String name = json.getString("name");
            String restaurant = json.getString("restaurant");
            String product = json.getString("product");
            String customerLocation = json.getString("customerLocation");
            String restaurantLocation = json.getString("restaurantLocation");
            String phone = json.getString("phone");
            register.newOrder(name, restaurant, product, customerLocation, restaurantLocation, phone);
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok().build();
    }

    @Override
    public Response getOrders(String restaurant) {
        List<Order> orders = register.listOrders(restaurant);
        JSONObject json = new JSONObject().put("register", new JSONArray(orders));
        return Response.ok(json.toString()).build();
    }
}
