package esi.buildit9.interop;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.rest.PurchaseOrderAssembler;
import esi.buildit9.rest.PurchaseOrderResource;

import javax.ws.rs.core.MediaType;

public class Team9Rest implements RentitInterop.Rest {

    public static final String RENTIT_POS = "https://rentit9.herokuapp.com/rest/pos";

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

    private void throwSubmissionFailed(int status) {
        String err = String.format("rentit9 po submission failed (%d)", status);
        throw new InteropException(err, null);
    }


}
