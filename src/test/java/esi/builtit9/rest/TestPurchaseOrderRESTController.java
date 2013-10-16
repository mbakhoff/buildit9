package esi.builtit9.rest;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import esi.buildit9.domain.RentIt;
import esi.buildit9.domain.Site;
import esi.buildit9.rest.PurchaseOrderLineResource;
import esi.buildit9.rest.PurchaseOrderResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.assertTrue;

@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestPurchaseOrderRESTController {

    public static final String URL_PO = "https://buildit9.herokuapp.com/rest/pos";

    @Test
    public void testResources() throws Exception {
        Client client = Client.create();
        WebResource webResource = client.resource(URL_PO);
        PurchaseOrderResource newResource = new PurchaseOrderResource();

        newResource.setBuildit("Buildit");
        newResource.setRentit("Rentit");
        newResource.setSiteAddress("Address");

        PurchaseOrderLineResource newResourceLine = new PurchaseOrderLineResource();
        newResourceLine.setEndDate(Calendar.getInstance());
        newResourceLine.setStartDate(Calendar.getInstance());
        newResourceLine.setTotalCost(1000000);
        newResourceLine.setPlantId("1");

        ArrayList<PurchaseOrderLineResource> newName = new ArrayList<PurchaseOrderLineResource>();
        newName.add(newResourceLine);
        newResource.setPurchaseOrderLines(newName);

        ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).post(ClientResponse.class, newResource);
        assertTrue(response.getStatus() == ClientResponse.Status.CREATED.getStatusCode());
    }
}
