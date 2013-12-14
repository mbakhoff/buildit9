package esi.buildit9.interop;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RemittanceAdvice;
import esi.buildit9.interop.rentit30.POStatus;
import esi.buildit9.interop.rentit30.PlantResourceList;
import esi.buildit9.interop.rentit30.PurchaseOrderResource;
import esi.buildit9.rest.PlantResource;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.context.ApplicationContext;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Team30Interop implements RentitInterop {

    public static final String RENTIT_PO = "http://rentit30.herokuapp.com/rest/po";
    public static final String RENTIT_PO_EXTEND = "http://rentit30.herokuapp.com/rest/po/update";
    public static final String RENTIT_PO_UPDATE = "http://rentit30.herokuapp.com/rest/po/modify";
    public static final String RENTIT_PO_CANCEL = "http://rentit30.herokuapp.com/rest/po/cancel";

    public static final String RENTIT_PLANTS="http://rentit30.herokuapp.com/rest/plants";
    public static final String RENTIT_PLANTS_AVAILABLE = "http://rentit30.herokuapp.com/rest/plants/available";

    @Override
    public void submitOrder(PurchaseOrder order) {

        String requestUrl = RENTIT_PO;

        PurchaseOrderResource result=submitOrder(order,requestUrl);

        order.setPlantExternalId(result.getPoId().toString());

        order.persist();
    }

    public PurchaseOrderResource submitOrder(PurchaseOrder order,String requestUrl){
        PurchaseOrderResource resource = toPOR(order);

        ClientResponse request = getClient().resource(requestUrl)
                .type(MediaType.APPLICATION_XML)
                .post(ClientResponse.class, resource);

        int status = request.getStatus();
        if (status != ClientResponse.Status.CREATED.getStatusCode()) {
            throw new RemoteHostException(request);
        }

        PurchaseOrderResource result = request.getEntity(PurchaseOrderResource.class);
        return result;
    }

    @Override
    public void updateOrder(PurchaseOrder order) {
        PurchaseOrderResource result = updateOrder(order,RENTIT_PO_UPDATE);
    }

    public PurchaseOrderResource updateOrder(PurchaseOrder order, String requestUrl){
        PurchaseOrderResource resource = toPOR(order);

        ClientResponse request = getClient().resource(requestUrl)
                .type(MediaType.APPLICATION_XML)
                .post(ClientResponse.class, resource);

        int status = request.getStatus();
        if (status != ClientResponse.Status.OK.getStatusCode()) {
            throw new RemoteHostException(request);
        }

        PurchaseOrderResource result = request.getEntity(PurchaseOrderResource.class);

        return result;
    }

    @Override
    public void extendOrder(PurchaseOrder order) {
        PurchaseOrderResource result = updateOrder(order,RENTIT_PO_EXTEND);
    }

    @Override
    public void cancelOrder(PurchaseOrder order) {
        String requestUrl= getCancelPurchaseOrderUrl(order);
        cancelOrder(order,requestUrl);
    }

    public PurchaseOrderResource cancelOrder(PurchaseOrder order,String requestUrl){

        WebResource webResource = getClient().resource(requestUrl);

        ClientResponse request = webResource.get(ClientResponse.class);

        if (request.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new RemoteHostException(request);
        }

        PurchaseOrderResource result= request.getEntity(PurchaseOrderResource.class);

        return result;
    }

    public String getCancelPurchaseOrderUrl(PurchaseOrder order){
        return RENTIT_PO_CANCEL + "/" + order.getPlantExternalId();
    }

    @Override
    public void submitRemittanceAdvice(ApplicationContext ctx, RemittanceAdvice remittanceAdvice) {

    }

    @Override
    public List<PlantResource> getAvailablePlantsBetween(String nameLike, Calendar startDate, Calendar endDate) {
        String requestUrl=getFindUrl(nameLike, new DateMidnight(startDate), new DateMidnight(endDate));

        WebResource webResource = getClient().resource(requestUrl);
        ClientResponse request = webResource.get(ClientResponse.class);

        if (request.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new RemoteHostException(request);
        }
        PlantResourceList plants= request.getEntity(esi.buildit9.interop.rentit30.PlantResourceList.class);

        return toLocalPlants(plants.getPlants());
    }



    private String getFindUrl(String name, DateMidnight startDate, DateMidnight endDate) {
        DateTimeFormatter fmt = ISODateTimeFormat.yearMonthDay();
        return String.format("%s?name=%s&start=%s&end=%s",
                RENTIT_PLANTS_AVAILABLE, name, startDate.toString(fmt), endDate.toString(fmt));
    }

    @Override
    public List<PlantResource> getPlants() {
        PlantResourceList plants= getAllPlantsResourceList();

        return toLocalPlants(plants.getPlants());
    }

    public PlantResourceList getAllPlantsResourceList(){
        WebResource webResource = getClient().resource(RENTIT_PLANTS);
        ClientResponse request = webResource.get(ClientResponse.class);

        if (request.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new RemoteHostException(request);
        }
        return request.getEntity(esi.buildit9.interop.rentit30.PlantResourceList.class);
    }

    private static List<PlantResource> toLocalPlants(List<esi.buildit9.interop.rentit30.PlantResource> plants) {
        List<PlantResource> local = new ArrayList<PlantResource>();
        for (esi.buildit9.interop.rentit30.PlantResource plant : plants) {
            local.add(toLocalPlant(plant));
        }
        return local;
    }

    private static PlantResource toLocalPlant(esi.buildit9.interop.rentit30.PlantResource plant) {
        PlantResource res = new PlantResource();
        res.setId(plant.getPlantId().longValue());
        res.setName(plant.getTitle());
        res.setPrice(plant.getPrice().floatValue());
        res.setDescription(plant.getDescription());
        return res;
    }

    private static Client getClient() {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("buildit", "buildit"));
        return client;
    }

    public esi.buildit9.interop.rentit30.PlantResource getPlant(String plantId){
        String requestUrl=RENTIT_PLANTS+"/"+plantId;

        WebResource webResource = getClient().resource(requestUrl);
        ClientResponse request = webResource.get(ClientResponse.class);

        if (request.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new RemoteHostException(request);
        }
        esi.buildit9.interop.rentit30.PlantResource plant= request.getEntity(esi.buildit9.interop.rentit30.PlantResource.class);

        return plant;
    }

    public PurchaseOrderResource toPOR(PurchaseOrder order){
        PurchaseOrderResource purchaseOrderResource = new PurchaseOrderResource();

        if (order.getIdAtRentit()!=null) {
            Long poid=Long.parseLong(order.getIdAtRentit());
            purchaseOrderResource.setPoId(poid);
        }

        purchaseOrderResource.setEnd(order.getEndDate().getTime());
        purchaseOrderResource.setStart(order.getStartDate().getTime());
        purchaseOrderResource.setCredentials("rentit:rentit");
        purchaseOrderResource.setEmail("");
        purchaseOrderResource.setServer("http://rentit30.herokuapp.com/rest/po/"+order.getId());
        purchaseOrderResource.setHireRequestId(order.getId());
        purchaseOrderResource.setPlant(getPlant(order.getPlantExternalId()));
        purchaseOrderResource.setPrice(order.getTotalPrice().doubleValue());
        purchaseOrderResource.setStatus(POStatus.PENDING_CONFIRMATION);

        return purchaseOrderResource;
    }
}
