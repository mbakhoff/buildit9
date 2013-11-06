package esi.buildit9.interop;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.rest.PlantResource;
import esi.buildit9.rest.PlantResourceList;
import esi.buildit9.rest.PurchaseOrderAssembler;
import esi.buildit9.rest.PurchaseOrderResource;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import javax.ws.rs.core.MediaType;
import java.util.Calendar;
import java.util.List;

public class Team9Rest implements RentitInterop.Rest {

    public static final String RENTIT_POS = "https://rentit9.herokuapp.com/rest/pos";
    public static final String RENTIT_PLANTS = "https://rentit9.herokuapp.com/rest/plants";

    private final PurchaseOrderAssembler assembler;

    public Team9Rest() {
        assembler = new PurchaseOrderAssembler();
    }

    @Override
    public void submitOrder(PurchaseOrder order) {
        PurchaseOrderResource res = assembler.toResource(order);
        ClientResponse createRequest = Client.create().resource(RENTIT_POS)
                .type(MediaType.APPLICATION_XML)
                .post(ClientResponse.class, res);

        int status = createRequest.getStatus();
        if (status != ClientResponse.Status.CREATED.getStatusCode()) {
            throwSubmissionFailed(status);
        }
    }

    @Override
    public List<PlantResource> getAvailablePlantsBetween(String nameLike, Calendar startDate, Calendar endDate) {
        WebResource webResource = Client.create().resource(
                getFindUrl(nameLike, new DateMidnight(startDate), new DateMidnight(endDate)));
        ClientResponse request = webResource.get(ClientResponse.class);

        if (request.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new InteropException("query response not HTTP 200", null);
        }
        return request.getEntity(PlantResourceList.class).getPlant();
    }

    private String getFindUrl(String name, DateMidnight startDate, DateMidnight endDate) {
        DateTimeFormatter fmt = ISODateTimeFormat.yearMonthDay();
        return String.format("%s/find?name=%s&start=%s&end=%s",
                RENTIT_PLANTS, name, startDate.toString(fmt), endDate.toString(fmt));
    }

    private void throwSubmissionFailed(int status) {
        String err = String.format("rentit9 po submission failed (%d)", status);
        throw new InteropException(err, null);
    }


}
