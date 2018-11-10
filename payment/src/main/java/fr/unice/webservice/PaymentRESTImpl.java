package fr.unice.webservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.bean.Pay;
import fr.unice.model.CreditCard;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Stateless
@Path("/")
public class PaymentRESTImpl implements PaymentREST {

    @EJB
    private Pay pay;

    @Override
    public Response pay(String message) {
        try {
            JSONObject json = new JSONObject(message);

            String cardNumber = json.getString("cardNumber");
            String expirationDate = json.getString("expirationDate");
            String cardSecurityCode = json.getString("cardSecurityCode");
            CreditCard creditCard = new CreditCard(cardNumber, expirationDate, cardSecurityCode);

            int orderId = json.getInt("orderId");
            pay.payOrder(creditCard, orderId);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok().build();
    }

}
