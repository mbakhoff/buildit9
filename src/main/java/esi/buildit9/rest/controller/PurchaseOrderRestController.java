package esi.buildit9.rest.controller;


import esi.buildit9.domain.*;
import esi.buildit9.rest.PurchaseOrderAssembler;
import esi.buildit9.rest.PurchaseOrderLineResource;
import esi.buildit9.rest.PurchaseOrderListResource;
import esi.buildit9.rest.PurchaseOrderResource;
import esi.buildit9.rest.util.MethodLookup;
import esi.buildit9.rest.util.MethodLookupHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/rest/")
public class PurchaseOrderRestController {

    public static final int METHOD_GET_ALL = 1;
    public static final int METHOD_CREATE_ORDER = 2;
    public static final int METHOD_GET_BY_ID = 3;
    public static final int METHOD_UPDATE_ORDER = 4;
    public static final int METHOD_CLOSE_BY_ID = 5;
    public static final int METHOD_APPROVE_BY_ID = 6;
    public static final int METHOD_REJECT_BY_ID = 7;

    public static final String HEADER_ENTITY_ID = "EntityId";

    private final PurchaseOrderAssembler assembler;
    private final MethodLookupHelper linker;

    public PurchaseOrderRestController() {
        assembler = new PurchaseOrderAssembler();
        linker = new MethodLookupHelper(PurchaseOrderRestController.class);
    }

    @RequestMapping(value="pos", method = RequestMethod.GET)
    @MethodLookup(METHOD_GET_ALL)
    public ResponseEntity<PurchaseOrderListResource> getAll() {
        List<PurchaseOrder> orders = PurchaseOrder.findAllPurchaseOrders();
        PurchaseOrderListResource resources = assembler.toResource(orders);
        return new ResponseEntity<PurchaseOrderListResource>(
                resources, HttpStatus.OK);
    }

    @RequestMapping(value = "pos", method = RequestMethod.POST)
    @MethodLookup(METHOD_CREATE_ORDER)
    public ResponseEntity<Void> createOrder(@RequestBody PurchaseOrderResource res) {
        PurchaseOrder order = new PurchaseOrderAssembler().fromResource(res);
        order.setOrderStatus(OrderStatus.WAITING_APPROVAL);

        order.persist();

        attachLines(order, res.getOrderLines().orderLines);

        HttpHeaders headers = new HttpHeaders();
        URI location =
                ServletUriComponentsBuilder.fromCurrentRequestUri().
                        pathSegment(order.getId().toString()).build().toUri();
        headers.setLocation(location);
        headers.add(HEADER_ENTITY_ID, order.getId().toString());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping("pos/{id}")
    @MethodLookup(METHOD_GET_BY_ID)
    public ResponseEntity<PurchaseOrderResource> getById(@PathVariable Long id) {
        PurchaseOrder order = PurchaseOrder.findPurchaseOrder(id);
        PurchaseOrderResource resources = assembler.toResource(order);
        resources.add(linker.buildLink(METHOD_CLOSE_BY_ID, order.getId()));
        resources.add(linker.buildLink(METHOD_UPDATE_ORDER, order.getId()));
        resources.add(linker.buildLink(METHOD_APPROVE_BY_ID, order.getId()));
        return new ResponseEntity<PurchaseOrderResource>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "pos/{id}", method = RequestMethod.PUT)
    @MethodLookup(METHOD_UPDATE_ORDER)
    public ResponseEntity<Void> updateOrder(@PathVariable Long id, @RequestBody PurchaseOrderResource res) {
        PurchaseOrder order = PurchaseOrder.findPurchaseOrder(id);
        order.setOrderStatus(OrderStatus.WAITING_APPROVAL);
        order.setRentit(RentIt.getOrCreateRentIt(res.getRentit()));
        order.setSite(Site.getOrCreateSite(res.getSiteAddress()));
        order.setSiteEngineerName(res.getSiteEngineerName());
        order.setTotalPrice(res.getTotalPrice());
        order.setWorksEngineerName(res.getWorksEngineerName());
        order.persist();

        deleteLines(order);

        attachLines(order, res.getOrderLines().orderLines);

        HttpHeaders headers = new HttpHeaders();
        URI location =
                ServletUriComponentsBuilder.fromCurrentRequestUri().
                        pathSegment(order.getId().toString()).build().toUri();
        headers.setLocation(location);
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }

    private void deleteLines(PurchaseOrder order) {
        for (PurchaseOrderLine line : order.getLines()) {
            line.remove();
        }
    }

    @RequestMapping(value = "pos/{id}", method = RequestMethod.DELETE)
    @MethodLookup(METHOD_CLOSE_BY_ID)
    public ResponseEntity<Void> closeOrder(@PathVariable Long id) {
        PurchaseOrder order = PurchaseOrder.findPurchaseOrder(id);
        order.setOrderStatus(OrderStatus.CLOSED);
        order.persist();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "pos/{id}/accept", method = RequestMethod.POST)
    @MethodLookup(METHOD_APPROVE_BY_ID)
    public ResponseEntity<PurchaseOrderResource> approveOrder(@PathVariable Long id) {
        PurchaseOrder order = PurchaseOrder.findPurchaseOrder(id);
        order.setOrderStatus(OrderStatus.APPROVED);
        order.persist();

        PurchaseOrderResource resource = assembler.toResource(order);
        resource.add(linker.buildLink(METHOD_UPDATE_ORDER, order.getId()));
        resource.add(linker.buildLink(METHOD_CLOSE_BY_ID, order.getId()));
        return new ResponseEntity<PurchaseOrderResource>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "pos/{id}/accept", method = RequestMethod.DELETE)
    @MethodLookup(METHOD_REJECT_BY_ID)
    public ResponseEntity<PurchaseOrderResource> rejectOrder(@PathVariable Long id) {
        PurchaseOrder order = PurchaseOrder.findPurchaseOrder(id);
        order.setOrderStatus(OrderStatus.REJECTED);
        order.persist();

        PurchaseOrderResource resource = assembler.toResource(order);
        resource.add(linker.buildLink(METHOD_UPDATE_ORDER, order.getId()));
        return new ResponseEntity<PurchaseOrderResource>(resource, HttpStatus.OK);
    }

    private void attachLines(PurchaseOrder order, List<PurchaseOrderLineResource> purchaseOrderLines) {
        for (PurchaseOrderLineResource res : purchaseOrderLines) {
            attachLine(order, res);
        }
    }

    private void attachLine(PurchaseOrder order, PurchaseOrderLineResource res) {
        PurchaseOrderLine line = new PurchaseOrderLine();
        line.setPlantExternalId(res.getPlantId());
        line.setPlantName(res.getPlantName());
        line.setStartDate(res.getStartDate());
        line.setEndDate(res.getEndDate());
        line.setTotalCost(res.getTotalCost()); // TODO: recalculate
        line.setPurchaseOrder(order);
        line.persist();
    }

}
