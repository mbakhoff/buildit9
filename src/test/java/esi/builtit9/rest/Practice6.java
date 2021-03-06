package esi.builtit9.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import esi.buildit9.rest.PurchaseOrderResource;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static esi.builtit9.rest.Commons.withBasicAuth;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Practice6 {

    @Test
    public void testDeliverable() throws Exception {
        // In our world, everything is a purchase order.

        // Create a Plant Hire Request using the RESTful service in Heroku.
        PurchaseOrderResource newResource = Commons.createPurchaseOrderResource();
        ClientResponse createRequest = withBasicAuth(Client.create()).resource(Commons.URL_POS)
                .type(MediaType.APPLICATION_XML)
                .post(ClientResponse.class, newResource);
        assertEquals(ClientResponse.Status.CREATED.getStatusCode(), createRequest.getStatus());

        // Obtain the Plant Hire Request resource
        PurchaseOrderResource purchaseOrder = getPurchaseOrder(Commons.getEntityId(createRequest));
        assertNotNull(purchaseOrder);

//        // This representation MUST have a hyperlink for its approval
//        ExtendedLink approvalLink = purchaseOrder.getByRel("approveOrder");
//        assertNotNull(approvalLink);
//
//        // Approve the obtained Plant Hire Request
//        ClientResponse approvalRequest = withBasicAuth(Client.create()).resource(approvalLink.getHref())
//                .accept(MediaType.APPLICATION_XML)
//                .post(ClientResponse.class);
//        assertEquals(ClientResponse.Status.OK.getStatusCode(), approvalRequest.getStatus());
//
//        // Obtain the Purchase Order resource (POresource) from the response of previous point
//        PurchaseOrderResource approvedOrder = approvalRequest.getEntity(PurchaseOrderResource.class);
//        assertNotNull(approvedOrder);
//        assertNotNull(approvedOrder.getByRel("self"));
//        assertEquals(OrderStatus.APPROVED, approvedOrder.getStatus());
    }

    private static PurchaseOrderResource getPurchaseOrder(String id) {
        return withBasicAuth(Client.create()).resource(Commons.URL_POS +"/"+ id).get(PurchaseOrderResource.class);
    }

}
