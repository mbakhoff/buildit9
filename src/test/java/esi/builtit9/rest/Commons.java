package esi.builtit9.rest;

import com.sun.jersey.api.client.ClientResponse;
import esi.buildit9.rest.PurchaseOrderLineListResource;
import esi.buildit9.rest.PurchaseOrderLineResource;
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

        PurchaseOrderLineResource newResourceLine = new PurchaseOrderLineResource();
        newResourceLine.setEndDate(Calendar.getInstance());
        newResourceLine.setStartDate(Calendar.getInstance());
        newResourceLine.setTotalCost(1000000);
        newResourceLine.setPlantId("1");

        PurchaseOrderLineListResource orderLineListResource =new PurchaseOrderLineListResource();
        orderLineListResource.orderLines.add(newResourceLine);
        newResource.setPurchaseOrderLines(orderLineListResource);

        return newResource;
    }

    public static String getEntityId(ClientResponse response) {
        String entityId = response.getHeaders().getFirst(PurchaseOrderRestController.HEADER_ENTITY_ID);
        if (entityId == null) {
            throw new RuntimeException("HTTP header did not contain entity id");
        }
        return entityId;
    }

}
