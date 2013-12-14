package esi.buildit9.rest.controller;


import esi.buildit9.RBAC;
import esi.buildit9.domain.OrderStatus;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.interop.RentitInterop;
import esi.buildit9.rest.PurchaseOrderAssembler;
import esi.buildit9.rest.PurchaseOrderListResource;
import esi.buildit9.rest.PurchaseOrderResource;
import esi.buildit9.rest.util.HttpHelpers;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rest/")
public class PurchaseOrderRestController {

    private final PurchaseOrderAssembler assembler;

    public PurchaseOrderRestController() {
        assembler = new PurchaseOrderAssembler();
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
    public ResponseEntity<PurchaseOrderListResource> getAll() {
        List<PurchaseOrder> orders = PurchaseOrder.findAllPurchaseOrders();

        PurchaseOrderListResource resources = assembler.toResource(orders);
        return new ResponseEntity<PurchaseOrderListResource>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "pos", method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResource> createOrder(@RequestBody PurchaseOrderResource res) {
        RBAC.assertAuthority(RBAC.ROLE_SITE_ENGINEER);

        PurchaseOrder order = new PurchaseOrder();
        assembler.fromResource(order, res);
        order.setOrderStatus(OrderStatus.WAITING_APPROVAL);
        order.persist();

        HttpHeaders headers = new HttpHeaders();
        headers.add("EntityId", order.getId().toString());
        PurchaseOrderResource resource = assembler.toResource(order);
        return new ResponseEntity<PurchaseOrderResource>(resource, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "pos/{id}", method = RequestMethod.GET)
    public ResponseEntity<PurchaseOrderResource> getById(@PathVariable Long id) {
        PurchaseOrder order = getCheckedOrder(id);

        PurchaseOrderResource resources = assembler.toResource(order);
        return new ResponseEntity<PurchaseOrderResource>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "pos/{id}", method = RequestMethod.PUT)
    public ResponseEntity<PurchaseOrderResource> updateOrder(@PathVariable Long id, @RequestBody PurchaseOrderResource res) {
        PurchaseOrder order = getCheckedOrder(id);

        if (order.getOrderStatus() == OrderStatus.WAITING_APPROVAL || order.getOrderStatus() == OrderStatus.CREATED) {
            RBAC.assertAuthority(RBAC.ROLE_WORKS_ENGINEER, RBAC.ROLE_RENTIT, RBAC.ROLE_SITE_ENGINEER);
        } else {
            RBAC.assertAuthority(RBAC.ROLE_WORKS_ENGINEER, RBAC.ROLE_RENTIT);
        }

        assembler.fromResource(order, res);
        order.persist();

        PurchaseOrderResource resource = assembler.toResource(order);
        return new ResponseEntity<PurchaseOrderResource>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "pos/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> closeOrder(@PathVariable Long id) {
        RBAC.assertAuthority(RBAC.ROLE_WORKS_ENGINEER);

        PurchaseOrder order = getCheckedOrder(id);
        order.setOrderStatus(OrderStatus.CLOSED);
        order.persist();

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "pos/{id}/weaccept", method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResource> approveOrder(@PathVariable Long id) {
        RBAC.assertAuthority(RBAC.ROLE_WORKS_ENGINEER);

        PurchaseOrder order = getCheckedOrder(id);
        order.setOrderStatus(OrderStatus.APPROVED);
        order.persist();

        sendToRentit(order);

        PurchaseOrderResource resource = assembler.toResource(order);
        return new ResponseEntity<PurchaseOrderResource>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "pos/{id}/wereject", method = RequestMethod.POST)
    public ResponseEntity<PurchaseOrderResource> rejectOrder(@PathVariable Long id) {
        RBAC.assertAuthority(RBAC.ROLE_WORKS_ENGINEER);

        PurchaseOrder order = getCheckedOrder(id);
        order.setOrderStatus(OrderStatus.REJECTED);
        order.persist();

        PurchaseOrderResource resource = assembler.toResource(order);
        return new ResponseEntity<PurchaseOrderResource>(resource, HttpStatus.OK);
    }

    private static void sendToRentit(PurchaseOrder order) {
        RentitInterop provider = order.getRentit().getInterop();
        provider.submitOrder(order);
    }

    @RequestMapping(value = "pos/{id}/rentitconfirm", method = RequestMethod.POST)
    public ResponseEntity<Void> confirmedByRentit(@PathVariable Long id) {
        RBAC.assertAuthority(RBAC.ROLE_RENTIT);

        PurchaseOrder order = getCheckedOrder(id);
        order.setOrderStatus(OrderStatus.RENTIT_CONFIRMED);
        order.persist();

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "pos/{id}/rentitreject", method = RequestMethod.POST)
    public ResponseEntity<Void> rejectedByRentit(@PathVariable Long id) {
        RBAC.assertAuthority(RBAC.ROLE_RENTIT);

        PurchaseOrder order = getCheckedOrder(id);
        order.setOrderStatus(OrderStatus.RENTIT_REJECTED);
        order.persist();

        return new ResponseEntity<Void>(HttpStatus.OK);
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
