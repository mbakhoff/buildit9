package esi.builtit9.rest;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import esi.buildit9.domain.OrderStatus;
import esi.buildit9.rest.PurchaseOrderLineListResource;
import esi.buildit9.rest.PurchaseOrderLineResource;
import esi.buildit9.rest.PurchaseOrderResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestPurchaseOrderRESTController {

    public static final String URL_POS = "https://buildit9.herokuapp.com/rest/pos";
    public static final String URL_PO = "https://buildit9.herokuapp.com/rest/po";

    @Test
    public void testResources() throws Exception {
        Client client = Client.create();
        WebResource webResource = client.resource(URL_POS);
        PurchaseOrderResource newResource = createPurchaseOrderResource();

        ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).post(ClientResponse.class, newResource);
        assertTrue(response.getStatus() == ClientResponse.Status.CREATED.getStatusCode());
    }

    @Test
    public void testCheckStatus() throws Exception {
        Client client = Client.create();
        WebResource webResource = client.resource(URL_POS);
        PurchaseOrderResource newResource = createPurchaseOrderResource();

        ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).post(ClientResponse.class, newResource);
        assertTrue(response.getStatus() == ClientResponse.Status.CREATED.getStatusCode());

        String id = response.getHeaders().getFirst("BuildItId");
        String requestUrl = URL_PO +"/"+id;

        WebResource webResourceById = client.resource(requestUrl);
        PurchaseOrderResource purchaseOrder = webResourceById.get(PurchaseOrderResource.class);

        assertEquals(OrderStatus.CREATED, purchaseOrder.getOrderStatus());
    }

    private PurchaseOrderResource createPurchaseOrderResource() {
        PurchaseOrderResource newResource = new PurchaseOrderResource();

        newResource.setBuildit("Buildit");
        newResource.setRentit("Rentit");
        newResource.setSiteAddress("Address");

        PurchaseOrderLineResource newResourceLine = new PurchaseOrderLineResource();
        newResourceLine.setEndDate(Calendar.getInstance());
        newResourceLine.setStartDate(Calendar.getInstance());
        newResourceLine.setTotalCost(1000000);
        newResourceLine.setPlantId("1");

        Set<PurchaseOrderLineResource> newName = new HashSet<PurchaseOrderLineResource>();
        newName.add(newResourceLine);

        PurchaseOrderLineListResource orderLineListResource =new PurchaseOrderLineListResource();

        newResource.setOrderLines(orderLineListResource);
        return newResource;
    }
}
