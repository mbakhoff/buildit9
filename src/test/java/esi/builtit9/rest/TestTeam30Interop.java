package esi.builtit9.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import esi.buildit9.domain.OrderStatus;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.interop.RemoteHostException;
import esi.buildit9.interop.Team30Interop;
import esi.buildit9.interop.rentit30.POStatus;
import esi.buildit9.interop.rentit30.PlantResource;
import esi.buildit9.interop.rentit30.PlantResourceList;
import esi.buildit9.interop.rentit30.PurchaseOrderResource;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestTeam30Interop {

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

    @Test
    public void testTeam30submitPurchaseOrder(){

        Team30Interop interop=new Team30Interop();

        String requestUrl = Team30Interop.RENTIT_PO;

        PurchaseOrder order = getDummyOrder();

        PurchaseOrderResource resource = interop.submitOrder(order,requestUrl);

        assertNotNull(resource);
    }

    @Test
    public void testTeam30extendPurchaseOrder(){

        Team30Interop interop=new Team30Interop();

        String requestUrl = Team30Interop.RENTIT_PO;

        PurchaseOrder order = getDummyOrder();

        PurchaseOrderResource resource = interop.submitOrder(order,requestUrl);

        DateMidnight date=new DateMidnight(resource.getEndDate()).plusDays(1);

        order.setIdAtRentit(resource.getPoId().toString());
        order.setEndDate(date.toGregorianCalendar());

        PurchaseOrderResource result=interop.updateOrder(order,Team30Interop.RENTIT_PO_EXTEND);

        assertEquals(date.toDate(),result.getEndDate());

    }

    @Test
    public void testTeam30updatePurchaseOrder(){

        Team30Interop interop=new Team30Interop();

        String requestUrl = Team30Interop.RENTIT_PO;

        PurchaseOrder order = getDummyOrder();

        PurchaseOrderResource resource = interop.submitOrder(order,requestUrl);

        DateMidnight startDate = new DateMidnight(resource.getStartDate()).plusDays(1);

        DateMidnight endDate=new DateMidnight(resource.getEndDate()).plusDays(1);

        //resource.setEndDate(date.toDate());

        order.setIdAtRentit(resource.getPoId().toString());
        order.setStartDate(startDate.toGregorianCalendar());
        order.setEndDate(endDate.toGregorianCalendar());

        PurchaseOrderResource result=interop.updateOrder(order,Team30Interop.RENTIT_PO_UPDATE);

        assertEquals(startDate.toDate(),result.getStartDate());

        assertEquals(endDate.toDate(),result.getEndDate());

    }

    @Test
    public void testTeam30cancelPurchaseOrder(){

        Team30Interop interop=new Team30Interop();

        String requestUrl = Team30Interop.RENTIT_PO;

        PurchaseOrder order = getDummyOrder();

        PurchaseOrderResource resource = interop.submitOrder(order,requestUrl);

        order.setIdAtRentit(resource.getPoId().toString());

        PurchaseOrderResource result=interop.cancelOrder(interop.getCancelPurchaseOrderUrl(order));

        assertEquals(result.getStatus(), POStatus.CANCELLED);

    }

    private PurchaseOrder getDummyOrder(){
        Team30Interop interop=new Team30Interop();

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE,1);

        PlantResource plant= interop.getAllPlantsResourceList().getPlants().get(1);

        PurchaseOrder order = new PurchaseOrder();
        order.setId(99999L);
        order.setStartDate(startDate);
        order.setEndDate(endDate);
        order.setOrderStatus(OrderStatus.APPROVED);

        order.setPlantName(plant.getTitle());
        order.setPlantExternalId(plant.getPlantId().toString());
        order.setTotalPrice(plant.getPrice().floatValue());

        return order;
    }

    private String getFindUrl(String name, DateMidnight startDate, DateMidnight endDate) {
        DateTimeFormatter fmt = ISODateTimeFormat.yearMonthDay();
        return String.format("%s?name=%s&start=%s&end=%s",
                Team30Interop.RENTIT_PLANTS_AVAILABLE, name, startDate.toString(fmt), endDate.toString(fmt));
    }

    private static Client getClient() {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("buildit", "buildit"));
        return client;
    }
}
