package esi.buildit9.interop;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RemittanceAdvice;
import esi.buildit9.interop.rentit30.PlantResourceList;
import esi.buildit9.rest.PlantResource;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Team30Interop implements RentitInterop {

    public static final String RENTIT_PLANTS="http://rentit30.herokuapp.com/rest/plants";
    public static final String RENTIT_PLANTS_AVAILABLE = "http://rentit30.herokuapp.com/rest/plants/available";

    @Override
    public void submitOrder(PurchaseOrder order) {


    }

    @Override
    public void updateOrder(PurchaseOrder order) {

    }

    @Override
    public void extendOrder(PurchaseOrder order) {

    }

    @Override
    public void cancelOrder(PurchaseOrder order) {

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

       WebResource webResource = getClient().resource(RENTIT_PLANTS);
        ClientResponse request = webResource.get(ClientResponse.class);

        if (request.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new RemoteHostException(request);
        }
        PlantResourceList plants= request.getEntity(esi.buildit9.interop.rentit30.PlantResourceList.class);

        return toLocalPlants(plants.getPlants());
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
}
