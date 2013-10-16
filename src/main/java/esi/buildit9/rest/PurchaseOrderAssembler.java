package esi.buildit9.rest;

import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RentIt;
import esi.buildit9.domain.Site;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PurchaseOrderAssembler {

    private PurchaseOrderLineAssembler lineAssembler;

    public PurchaseOrderAssembler() {
        lineAssembler = new PurchaseOrderLineAssembler();
    }

    public PurchaseOrderResource toResource(PurchaseOrder order) {
        PurchaseOrderResource res = new PurchaseOrderResource();
        res.setBuildit("builtit9"); //TODO this must e changed.. someday
        res.setSiteAddress(order.getSite().getAddress());
        res.setOrderLines(lineAssembler.toResource(order.getLines())); //.setPurchaseOrderLines(lineAssembler.toResource(order.getLines()));
        return res;
    }

    public PurchaseOrderListResource toResource(List<PurchaseOrder> orders) {
        Set<PurchaseOrderResource> all = new HashSet<PurchaseOrderResource>();
        for (PurchaseOrder order : orders) {
            all.add(toResource(order));
        }
        PurchaseOrderListResource purchaseOrderListResource = new PurchaseOrderListResource();
        purchaseOrderListResource.purchaseOrders=all;
        return purchaseOrderListResource;
    }

    public PurchaseOrder fromResource(PurchaseOrderResource res) {
        PurchaseOrder order = new PurchaseOrder();
        order.setRentit(RentIt.getOrCreateRentIt(res.getRentit()));
        order.setSite(Site.getOrCreateSite(res.getSiteAddress()));
        order.setOrderStatus(res.getOrderStatus());
        order.setSiteEngineerName(res.getSiteEngineerName());
        order.setWorksEngineerName(res.getWorksEngineerName());
        return order;
    }

}
