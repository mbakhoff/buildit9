package esi.buildit9.interop;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import esi.buildit9.domain.Invoice;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RemittanceAdvice;
import esi.buildit9.domain.RentIt;
import esi.buildit9.interop.rentit1.POExtensionResource;
import esi.buildit9.interop.rentit1.POstatus;
import esi.buildit9.interop.rentit1.PlantResourceList;
import esi.buildit9.interop.rentit1.PurchaseOrderResource;
import esi.buildit9.rest.PlantResource;
import esi.buildit9.service.InvoiceHelper;
import esi.buildit9.service.InvoiceResource;
import esi.buildit9.service.InvoiceSDO;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.w3c.dom.Document;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Team1Interop implements RentitInterop {

    private static final String RENTIT_PLANTS = "https://rentit1.herokuapp.com/rest/plants";
    private static final String RENTIT_POS = "https://rentit1.herokuapp.com/rest/pos";

    @Override
    public void submitOrder(PurchaseOrder order) {
        PurchaseOrderResource res = prepareOrder(order);

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

    private PurchaseOrderResource prepareOrder(PurchaseOrder order) {
        esi.buildit9.interop.rentit1.PlantResource plant = new esi.buildit9.interop.rentit1.PlantResource();
        plant.setId(Long.parseLong(order.getPlantExternalId()));

        PurchaseOrderResource res = new PurchaseOrderResource();
        res.setPoExtensionRejectionlink(getRejectUrl(order));
        res.setPoRejectionlink(getRejectUrl(order));
        res.setPlantId(plant);
        res.setCommentt("request from builtit9");
        res.setCustomerEmail("buildit9esi@gmail.com");
        res.setStartdate(order.getStartDate().getTime());
        res.setEnddate(order.getEndDate().getTime());
        res.setPrice(BigDecimal.valueOf(order.getTotalPrice()));
        res.setStatus(POstatus.PENDING_CONFIRMATION);
        return res;
    }

    @Override
    public void updateOrder(PurchaseOrder order) {
        PurchaseOrderResource res = prepareOrder(order);

        ClientResponse updateRequest = getClient().resource(RENTIT_POS + "/" + order.getIdAtRentit())
                .type(MediaType.APPLICATION_XML)
                .post(ClientResponse.class, res);

        if (updateRequest.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new RemoteHostException(updateRequest);
        }
    }

    @Override
    public void extendOrder(PurchaseOrder order) {
        POExtensionResource res = new POExtensionResource();
        res.setStartdate(order.getStartDate().getTime());
        res.setEnddate(order.getEndDate().getTime());
        res.setPrice(BigDecimal.valueOf(order.getTotalPrice()));

        ClientResponse extendRequest = getClient().resource(RENTIT_POS + "/" + order.getIdAtRentit() + "/updates")
                .type(MediaType.APPLICATION_XML)
                .post(ClientResponse.class, res);

        if (extendRequest.getStatus() != ClientResponse.Status.CREATED.getStatusCode()) {
            throw new RemoteHostException(extendRequest);
        }
    }

    @Override
    public void cancelOrder(PurchaseOrder order) {
        ClientResponse updateRequest = getClient().resource(RENTIT_POS + "/" + order.getIdAtRentit() + "/cancel")
                .type(MediaType.APPLICATION_XML)
                .delete(ClientResponse.class);
        if (updateRequest.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new RemoteHostException(updateRequest);
        }
    }

    private String getRejectUrl(PurchaseOrder order) {
        return String.format("https://buildit9.herokuapp.com/rest/pos/%d/rentitreject", order.getId());
    }

    private Long extractCreatedId(ClientResponse createRequest) {
        String location = createRequest.getHeaders().getFirst("Location");
        return Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
    }

    @Override
    public void submitRemittanceAdvice(ApplicationContext ctx, RemittanceAdvice remittanceAdvice) {
        JavaMailSender sender = ctx.getBean(JavaMailSender.class);
        Invoice invoice = remittanceAdvice.getInvoice();
        try {
            MimeMessage msg = sender.createMimeMessage();
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(invoice.getSenderEmail()));
            msg.setSubject("remittance advice");
            msg.setText(String.format("Remittance Advice for Purchase Order Id:-%d-\nInvoice Id:-%d-\n",
                    invoice.getPurchaseOrder().getId(),
                    invoice.getId()));
            sender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
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

    @Override
    public InvoiceSDO parseInvoice(RentIt rentIt, Document document) {
        InvoiceResource res = InvoiceHelper.unmarshall(document, InvoiceResource.class);
        return new InvoiceSDO(rentIt, res.getId(), res.getPo(), res.getTotal());
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
