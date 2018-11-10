package fr.unice.webservices;

import fr.unice.model.ETA;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Produces({"application/json"})
public interface KitchenREST {

    @Path("/eta/{restaurant}/{meal}")
    @GET
    ETA getETA(@PathParam("restaurant") String restaurant, @PathParam("meal") String meal);

}
