package fr.unice.webservices;

import fr.unice.model.ETA;

import javax.ws.rs.Path;

@Path("rest/kitchen")
public class KitchenRESTImpl implements KitchenREST {

    @Override
    public ETA getETA(String restaurant, String meal) {
        return new ETA(45);
    }

}
