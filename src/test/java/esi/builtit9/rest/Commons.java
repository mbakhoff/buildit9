package esi.builtit9.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import esi.buildit9.rest.PurchaseOrderResource;
import esi.buildit9.rest.controller.PurchaseOrderRestController;

import java.util.Calendar;

public class Commons {
    public static final String URL_POS = "https://buildit9.herokuapp.com/rest/pos";

    public static PurchaseOrderResource createPurchaseOrderResource() {
        PurchaseOrderResource newResource = new PurchaseOrderResource();

        newResource.setBuildit("Buildit");
        newResource.setRentit("Rentit");
        newResource.setSiteAddress("Address");
        newResource.setEndDate(Calendar.getInstance());
        newResource.setStartDate(Calendar.getInstance());
        newResource.setTotalPrice(1000000);
        newResource.setPlantId("1");

        return newResource;
    }

    public static String getEntityId(ClientResponse response) {
        String entityId = response.getHeaders().getFirst(PurchaseOrderRestController.HEADER_ENTITY_ID);
        if (entityId == null) {
            throw new RuntimeException("HTTP header did not contain entity id");
        }
        return entityId;
    }

    public static Client withBasicAuth(Client client) {
        client.addFilter(new HTTPBasicAuthFilter("admin", "admin"));
        return client;
    }

    public static Client withBasicAuthUser(Client client) {
        client.addFilter(new HTTPBasicAuthFilter("user", "user"));
        return client;
    }
    public static Client withBasicAuthSiteEngineer(Client client) {
        client.addFilter(new HTTPBasicAuthFilter("siteEngineer", "user"));
        return client;
    }
    public static Client withBasicAuthWorksEngineer(Client client) {
        client.addFilter(new HTTPBasicAuthFilter("worksEngineer", "user"));
        return client;
    }
    public static Client withBasicAuthRentit(Client client) {
        client.addFilter(new HTTPBasicAuthFilter("rentit", "rentit"));
        return client;
    }
}
