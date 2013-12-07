package esi.buildit9.interop;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RemittanceAdvice;
import esi.buildit9.rest.*;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import javax.ws.rs.core.MediaType;
import java.util.Calendar;
import java.util.List;

public class Team9Interop implements RentitInterop {

    public static final String RENTIT_POS = "https://rentit9.herokuapp.com/rest/pos";
    public static final String RENTIT_PLANTS = "https://rentit9.herokuapp.com/rest/plants";
    public static final String RENTIT_RA = "https://rentit9.herokuapp.com/rest/ra";

    private final PurchaseOrderAssembler assembler;
    private final RemittanceAdviceAssembler remittanceAssembler;

    public Team9Interop() {
        assembler = new PurchaseOrderAssembler();
        remittanceAssembler = new RemittanceAdviceAssembler();
    }

    @Override
    public void submitOrder(PurchaseOrder order) {
        PurchaseOrderResource res = assembler.toResource(order);
        ClientResponse createRequest = getClient().resource(RENTIT_POS)
                .type(MediaType.APPLICATION_XML)
                .post(ClientResponse.class, res);

        int status = createRequest.getStatus();
        if (status != ClientResponse.Status.CREATED.getStatusCode()) {
            throw new RemoteHostException(createRequest);
        }
    }

    @Override
    public List<PlantResource> getAvailablePlantsBetween(String nameLike, Calendar startDate, Calendar endDate) {
        WebResource webResource = getClient().resource(
                getFindUrl(nameLike, new DateMidnight(startDate), new DateMidnight(endDate)));
        ClientResponse request = webResource.get(ClientResponse.class);

        if (request.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new RemoteHostException(request);
        }
        return request.getEntity(PlantResourceList.class).getPlants();
    }

    @Override
    public List<PlantResource> getPlants() {
        WebResource webResource = getClient().resource(RENTIT_PLANTS);
        ClientResponse request = webResource.get(ClientResponse.class);

        if (request.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new RemoteHostException(request);
        }
        return request.getEntity(PlantResourceList.class).getPlants();
    }

    private String getFindUrl(String name, DateMidnight startDate, DateMidnight endDate) {
        DateTimeFormatter fmt = ISODateTimeFormat.yearMonthDay();
        return String.format("%s/find?name=%s&start=%s&end=%s",
                RENTIT_PLANTS, name, startDate.toString(fmt), endDate.toString(fmt));
    }

    @Override
	public void submitRemittanceAdvice(RemittanceAdvice remittanceAdvice) {
		RemittanceAdviceResource res = remittanceAssembler.toResource(remittanceAdvice);
        ClientResponse createRequest = getClient().resource(RENTIT_RA)
                .type(MediaType.APPLICATION_XML)
                .post(ClientResponse.class, res);

        int status = createRequest.getStatus();
        if (status != ClientResponse.Status.CREATED.getStatusCode()) {
            throw new RemoteHostException(createRequest);
        }
	}

    private static Client getClient() {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("buildit9", "buildit9"));
        return client;
    }

}
