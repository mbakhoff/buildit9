package esi.buildit9.rest.controller;


import esi.buildit9.RBAC;
import esi.buildit9.domain.OrderStatus;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RentIt;
import esi.buildit9.domain.Site;
import esi.buildit9.interop.InteropImplementation;
import esi.buildit9.rest.PurchaseOrderAssembler;
import esi.buildit9.rest.PurchaseOrderListResource;
import esi.buildit9.rest.PurchaseOrderResource;
import esi.buildit9.rest.util.HttpHelpers;
import esi.buildit9.rest.util.MethodLookup;
import esi.buildit9.rest.util.MethodLookupHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    private static final int METHOD_RENTIT_CONFIRMED = 8;
    private static final int METHOD_RENTIT_REJECTED = 9;

    public static final String HEADER_ENTITY_ID = "EntityId";

    private final PurchaseOrderAssembler assembler;
    private final MethodLookupHelper linker;

    public PurchaseOrderRestController() {
        assembler = new PurchaseOrderAssembler();
        linker = new MethodLookupHelper(PurchaseOrderRestController.class);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        ex.printStackTrace();
        if (ex instanceof RBAC.UnauthorizedAccessException) {
            return new ResponseEntity<String>(HttpHelpers.getStack(ex), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<String>(HttpHelpers.getStack(ex), HttpStatus.INTERNAL_SERVER_ERROR);
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
        RBAC.assertAuthority(RBAC.ROLE_SITE_ENGINEER);

        PurchaseOrder order = new PurchaseOrderAssembler().fromResource(res);
        order.setOrderStatus(OrderStatus.WAITING_APPROVAL);
        order.setPlantExternalId(res.getPlantId());
        order.setPlantName(res.getPlantName());
        order.setStartDate(res.getStartDate());
        order.setEndDate(res.getEndDate());
        order.persist();

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
        PurchaseOrder order = getCheckedOrder(id);
        PurchaseOrderResource resources = assembler.toResource(order);
        resources.add(linker.buildLink(METHOD_CLOSE_BY_ID, order.getId()));
        resources.add(linker.buildLink(METHOD_UPDATE_ORDER, order.getId()));
        resources.add(linker.buildLink(METHOD_APPROVE_BY_ID, order.getId()));
        return new ResponseEntity<PurchaseOrderResource>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "pos/{id}", method = RequestMethod.PUT)
    @MethodLookup(METHOD_UPDATE_ORDER)
    public ResponseEntity<Void> updateOrder(@PathVariable Long id, @RequestBody PurchaseOrderResource res) {
        PurchaseOrder order = getCheckedOrder(id);

        if (order.getOrderStatus() == OrderStatus.WAITING_APPROVAL || order.getOrderStatus() == OrderStatus.CREATED) {
            RBAC.assertAuthority(RBAC.ROLE_WORKS_ENGINEER, RBAC.ROLE_RENTIT, RBAC.ROLE_SITE_ENGINEER);
        } else {
            RBAC.assertAuthority(RBAC.ROLE_WORKS_ENGINEER, RBAC.ROLE_RENTIT);
        }

        // i don't we should allow updating the status randomly
        //order.setOrderStatus(OrderStatus.WAITING_APPROVAL);

        order.setRentit(RentIt.getOrCreateRentIt(res.getRentit()));
        order.setSite(Site.getOrCreateSite(res.getSiteAddress()));
        order.setSiteEngineerName(res.getSiteEngineerName());
        order.setTotalPrice(res.getTotalPrice());
        order.setWorksEngineerName(res.getWorksEngineerName());
        order.setPlantExternalId(res.getPlantId());
        order.setPlantName(res.getPlantName());
        order.setStartDate(res.getStartDate());
        order.setEndDate(res.getEndDate());
        order.persist();

        HttpHeaders headers = new HttpHeaders();
        URI location =
                ServletUriComponentsBuilder.fromCurrentRequestUri().
                        pathSegment(order.getId().toString()).build().toUri();
        headers.setLocation(location);
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "pos/{id}", method = RequestMethod.DELETE)
    @MethodLookup(METHOD_CLOSE_BY_ID)
    public ResponseEntity<Void> closeOrder(@PathVariable Long id) {
        RBAC.assertAuthority(RBAC.ROLE_WORKS_ENGINEER);

        PurchaseOrder order = getCheckedOrder(id);
        order.setOrderStatus(OrderStatus.CLOSED);
        order.persist();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "pos/{id}/accept", method = RequestMethod.POST)
    @MethodLookup(METHOD_APPROVE_BY_ID)
    public ResponseEntity<PurchaseOrderResource> approveOrder(@PathVariable Long id) {
        RBAC.assertAuthority(RBAC.ROLE_WORKS_ENGINEER);

        PurchaseOrder order = getCheckedOrder(id);
        order.setOrderStatus(OrderStatus.APPROVED);
        order.persist();

        sendToRentit(order);

        PurchaseOrderResource resource = assembler.toResource(order);
        resource.add(linker.buildLink(METHOD_UPDATE_ORDER, order.getId()));
        resource.add(linker.buildLink(METHOD_CLOSE_BY_ID, order.getId()));
        return new ResponseEntity<PurchaseOrderResource>(resource, HttpStatus.OK);
    }

    private void sendToRentit(PurchaseOrder order) {
        InteropImplementation provider = order.getRentit().getProvider();
        if (provider == null) {
            provider = InteropImplementation.Team9;
        }
        provider.getRest().submitOrder(order);
    }

    @RequestMapping(value = "pos/{id}/confirm", method = RequestMethod.POST)
    @MethodLookup(METHOD_RENTIT_CONFIRMED)
    public ResponseEntity<Void> confirmedOrder(@PathVariable Long id) {
        RBAC.assertAuthority(RBAC.ROLE_RENTIT);

        PurchaseOrder order = getCheckedOrder(id);
        order.setOrderStatus(OrderStatus.RENTIT_CONFIRMED);
        order.persist();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "pos/{id}/confirm", method = RequestMethod.DELETE)
    @MethodLookup(METHOD_RENTIT_REJECTED)
    public ResponseEntity<Void> rejectedOrder(@PathVariable Long id) {
        RBAC.assertAuthority(RBAC.ROLE_RENTIT);

        PurchaseOrder order = getCheckedOrder(id);
        order.setOrderStatus(OrderStatus.RENTIT_REJECTED);
        order.persist();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "pos/{id}/accept", method = RequestMethod.DELETE)
    @MethodLookup(METHOD_REJECT_BY_ID)
    public ResponseEntity<PurchaseOrderResource> rejectOrder(@PathVariable Long id) {
        RBAC.assertAuthority(RBAC.ROLE_WORKS_ENGINEER);

        PurchaseOrder order = getCheckedOrder(id);
        order.setOrderStatus(OrderStatus.REJECTED);
        order.persist();

        PurchaseOrderResource resource = assembler.toResource(order);
        resource.add(linker.buildLink(METHOD_UPDATE_ORDER, order.getId()));
        return new ResponseEntity<PurchaseOrderResource>(resource, HttpStatus.OK);
    }

    private static PurchaseOrder getCheckedOrder(Long id) {
        PurchaseOrder order = PurchaseOrder.findPurchaseOrder(id);
        if (order == null) {
            throw new OrderNotFound(id);
        }
        return order;
    }

    public static class OrderNotFound extends RuntimeException {
        public OrderNotFound(Long id) {
            super(String.format("Order %d not found", id));
        }
    }

}
