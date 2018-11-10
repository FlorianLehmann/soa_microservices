package fr.unice.webservice;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface PaymentREST {

    @POST
    @Path("/pay")
    @Produces(MediaType.APPLICATION_JSON)
    Response pay(String message);

}
