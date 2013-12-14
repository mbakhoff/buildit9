package esi.builtit9.rest;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import esi.buildit9.domain.OrderStatus;
import esi.buildit9.rest.PurchaseOrderResource;
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

}
