package esi.builtit9.rest;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import esi.buildit9.domain.OrderStatus;
import esi.buildit9.interop.RemoteHostException;
import esi.buildit9.interop.rentit30.PlantResourceList;
import esi.buildit9.rest.PurchaseOrderResource;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static esi.builtit9.rest.Commons.*;
import static org.junit.Assert.*;


public class TestPurchaseOrderRESTController {

    @Test
    public void testCreateResources() throws Exception {
        Client client = withBasicAuth(Client.create());
        WebResource webResource = client.resource(Commons.URL_POS);
        PurchaseOrderResource newResource = Commons.createPurchaseOrderResource();

        ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).post(ClientResponse.class, newResource);
        assertTrue(response.getStatus() == ClientResponse.Status.CREATED.getStatusCode());
    }

    @Test
    public void testCreateResourcesUser() throws Exception {
        Client client = withBasicAuthUser(Client.create());
        WebResource webResource = client.resource(Commons.URL_POS);
        PurchaseOrderResource newResource = Commons.createPurchaseOrderResource();

        ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).post(ClientResponse.class, newResource);
        assertFalse(response.getStatus() == ClientResponse.Status.CREATED.getStatusCode());
    }

    @Test
    public void testCreateResourcesSiteEngineer() throws Exception {
        Client client = withBasicAuthSiteEngineer(Client.create());
        WebResource webResource = client.resource(Commons.URL_POS);
        PurchaseOrderResource newResource = Commons.createPurchaseOrderResource();

        ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).post(ClientResponse.class, newResource);
        assertTrue(response.getStatus() == ClientResponse.Status.CREATED.getStatusCode());
    }

    @Test
    public void testCreateResourcesWorksEngineer() throws Exception {
        Client client = withBasicAuthWorksEngineer(Client.create());
        WebResource webResource = client.resource(Commons.URL_POS);
        PurchaseOrderResource newResource = Commons.createPurchaseOrderResource();

        ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).post(ClientResponse.class, newResource);
        assertFalse(response.getStatus() == ClientResponse.Status.CREATED.getStatusCode());
    }

    @Test
    public void testCreateResourcesRentit() throws Exception {
        Client client = withBasicAuthRentit(Client.create());
        WebResource webResource = client.resource(Commons.URL_POS);
        PurchaseOrderResource newResource = Commons.createPurchaseOrderResource();

        ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).post(ClientResponse.class, newResource);
        assertFalse(response.getStatus() == ClientResponse.Status.CREATED.getStatusCode());
    }

    @Test
    public void testModifyPurchaseOrder() throws Exception {
        Client client = withBasicAuth(Client.create());
        WebResource webResource = client.resource(Commons.URL_POS);
        PurchaseOrderResource newResource = Commons.createPurchaseOrderResource();

        ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).post(ClientResponse.class, newResource);

        String requestUrl = Commons.URL_POS +"/"+ Commons.getEntityId(response);

        PurchaseOrderResource modifiedResource = Commons.createPurchaseOrderResource();
        Commons.createPurchaseOrderResource().setSiteAddress("ModifiedAddress");

        webResource = client.resource(requestUrl);
        response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).put(ClientResponse.class, modifiedResource);
        assertTrue(response.getStatus() == ClientResponse.Status.OK.getStatusCode());

    }

    @Test
    public void testModifyPurchaseOrderSiteEngineer() throws Exception {
        Client client = withBasicAuthSiteEngineer(Client.create());
        WebResource webResource = client.resource(Commons.URL_POS);
        PurchaseOrderResource newResource = Commons.createPurchaseOrderResource();

        ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).post(ClientResponse.class, newResource);

        String requestUrl = Commons.URL_POS +"/"+ Commons.getEntityId(response);

        PurchaseOrderResource modifiedResource = Commons.createPurchaseOrderResource();
        Commons.createPurchaseOrderResource().setSiteAddress("ModifiedAddress");

        webResource = client.resource(requestUrl);
        response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).put(ClientResponse.class, modifiedResource);
        assertTrue(response.getStatus() == ClientResponse.Status.OK.getStatusCode());

    }

    @Test
    public void testCancelPurchaseOrder() throws Exception {
        Client client = withBasicAuth(Client.create());
        WebResource webResource = client.resource(Commons.URL_POS);
        PurchaseOrderResource newResource = Commons.createPurchaseOrderResource();

        ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).post(ClientResponse.class, newResource);

        String requestUrl = Commons.URL_POS +"/"+ Commons.getEntityId(response);

        webResource = client.resource(requestUrl);
        response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).delete(ClientResponse.class);
        assertTrue(response.getStatus() == ClientResponse.Status.OK.getStatusCode());

    }

    @Test
    public void testCancelPurchaseOrderSite() throws Exception {
        Client client = withBasicAuthSiteEngineer(Client.create());
        WebResource webResource = client.resource(Commons.URL_POS);
        PurchaseOrderResource newResource = Commons.createPurchaseOrderResource();

        ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).post(ClientResponse.class, newResource);

        String requestUrl = Commons.URL_POS +"/"+ Commons.getEntityId(response);

        webResource = client.resource(requestUrl);
        response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).delete(ClientResponse.class);
        assertFalse(response.getStatus() == ClientResponse.Status.OK.getStatusCode());

    }

    @Test
    public void testCheckStatus() throws Exception {
        Client client = withBasicAuth(Client.create());
        WebResource webResource = client.resource(Commons.URL_POS);
        PurchaseOrderResource newResource = Commons.createPurchaseOrderResource();

        ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).post(ClientResponse.class, newResource);
        assertTrue(response.getStatus() == ClientResponse.Status.CREATED.getStatusCode());

        String requestUrl = Commons.URL_POS +"/"+ Commons.getEntityId(response);

        WebResource webResourceById = client.resource(requestUrl);
        PurchaseOrderResource purchaseOrder = webResourceById.get(PurchaseOrderResource.class);

        assertEquals(OrderStatus.WAITING_APPROVAL.toString(), purchaseOrder.getStatus());
    }

    @Test
    public void testCheckStatusSite() throws Exception {
        Client client = withBasicAuthSiteEngineer(Client.create());
        WebResource webResource = client.resource(Commons.URL_POS);
        PurchaseOrderResource newResource = Commons.createPurchaseOrderResource();

        ClientResponse response = webResource.type(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML).post(ClientResponse.class, newResource);
        assertTrue(response.getStatus() == ClientResponse.Status.CREATED.getStatusCode());

        String requestUrl = Commons.URL_POS +"/"+ Commons.getEntityId(response);

        WebResource webResourceById = client.resource(requestUrl);
        PurchaseOrderResource purchaseOrder = webResourceById.get(PurchaseOrderResource.class);

        assertEquals(OrderStatus.WAITING_APPROVAL.toString(), purchaseOrder.getStatus());
    }

    @Test
    public void testTeam30InteropSearch(){
        String requestUrl=getFindUrl("Truck", new DateMidnight(), new DateMidnight().plus(1));

        WebResource webResource = getClient().resource(requestUrl);
        ClientResponse request = webResource.get(ClientResponse.class);

        if (request.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new RemoteHostException(request);
        }
        PlantResourceList plants= request.getEntity(esi.buildit9.interop.rentit30.PlantResourceList.class);

        assertNotNull(plants);
    }

    private String getFindUrl(String name, DateMidnight startDate, DateMidnight endDate) {
        DateTimeFormatter fmt = ISODateTimeFormat.yearMonthDay();
        return String.format("%s?name=%s&start=%s&end=%s",
                "http://rentit30.herokuapp.com/rest/plants/available", name, startDate.toString(fmt), endDate.toString(fmt));
    }

    private static Client getClient() {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("buildit", "buildit"));
        return client;
    }

}
