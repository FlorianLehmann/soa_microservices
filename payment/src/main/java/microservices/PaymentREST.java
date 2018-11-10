package microservices;

import javax.ws.rs.*;
import java.text.ParseException;
import java.util.List;

@Produces({"application/json"})
public interface PaymentREST {
    /*
        {
            cardNumber: String,
            expirationDate: String,
            cardSecurityCode: String,
            commandN: string
        }
    */
    @Path("/payment")
    @POST
    public void payment(String message) throws ParseException;
}
