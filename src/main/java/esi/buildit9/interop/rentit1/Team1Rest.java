package esi.buildit9.interop.rentit1;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RemittanceAdvice;
import esi.buildit9.interop.RemoteHostException;
import esi.buildit9.interop.RentitInterop;
import esi.buildit9.rest.PlantResource;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Team1Rest implements RentitInterop.Rest {

    private static final String RENTIT_PLANTS = "https://rentit1.herokuapp.com/rest/plants";
    private static final String RENTIT_POS = "https://rentit1.herokuapp.com/rest/pos";

    @Override
    public void submitOrder(PurchaseOrder order) {
        esi.buildit9.interop.rentit1.PlantResource plant = new esi.buildit9.interop.rentit1.PlantResource();
        plant.setId(Long.parseLong(order.getPlantExternalId()));

        esi.buildit9.interop.rentit1.PurchaseOrderResource res = new PurchaseOrderResource();
        res.setPoExtensionRejectionlink(null); // TODO
        res.setPoRejectionlink(null); // TODO
        res.setPlantId(plant);
        res.setCommentt("request from builtit9");
        res.setCustomerEmail("buildit9esi@gmail.com");
        res.setStartdate(order.getStartDate().getTime());
        res.setEnddate(order.getEndDate().getTime());
        res.setPrice(BigDecimal.valueOf(order.getTotalPrice()));
        res.setStatus(POstatus.OPEN);

        ClientResponse createRequest = getClient().resource(RENTIT_POS)
                .type(MediaType.APPLICATION_XML)
                .post(ClientResponse.class, res);

        int status = createRequest.getStatus();
        if (status == ClientResponse.Status.CREATED.getStatusCode()) {
            order.setIdAtRentit(extractCreatedId(createRequest).toString());
            order.merge();
        } else {
            throw new RemoteHostException(createRequest);
        }
    }

    private Long extractCreatedId(ClientResponse createRequest) {
        String location = createRequest.getHeaders().getFirst("Location");
        return Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
    }

    @Override
    public void submitRemittanceAdvice(RemittanceAdvice remittanceAdvice) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<PlantResource> getAvailablePlantsBetween(String nameLike, Calendar startDate, Calendar endDate) {
        WebResource webResource = getClient().resource(
                getFindUrl(nameLike, new DateMidnight(startDate), new DateMidnight(endDate)));
        ClientResponse request = webResource.get(ClientResponse.class);

        if (request.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new RemoteHostException(request);
        }
        return toLocalPlants(request.getEntity(PlantResourceList.class).getPlantResources());
    }

    private String getFindUrl(String name, DateMidnight startDate, DateMidnight endDate) {
        DateTimeFormatter fmt = ISODateTimeFormat.yearMonthDay();
        return String.format("%s?plantName=%s&startDate=%s&endDate=%s",
                RENTIT_PLANTS, name, startDate.toString(fmt), endDate.toString(fmt));
    }

    @Override
    public List<PlantResource> getPlants() {
        WebResource webResource = getClient().resource(RENTIT_PLANTS);
        ClientResponse request = webResource.get(ClientResponse.class);

        if (request.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new RemoteHostException(request);
        }
        return toLocalPlants(request.getEntity(PlantResourceList.class).getPlantResources());
    }

    private static List<PlantResource> toLocalPlants(List<esi.buildit9.interop.rentit1.PlantResource> plants) {
        List<PlantResource> local = new ArrayList<PlantResource>();
        for (esi.buildit9.interop.rentit1.PlantResource plant : plants) {
            local.add(toLocalPlant(plant));
        }
        return local;
    }

    private static PlantResource toLocalPlant(esi.buildit9.interop.rentit1.PlantResource plant) {
        PlantResource res = new PlantResource();
        res.setId(plant.getId());
        res.setName(plant.getName());
        res.setPrice(plant.getPricePrDay().floatValue());
        res.setDescription("");
        return res;
    }

    private static Client getClient() {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("customer", "customer"));
        return client;
    }
}