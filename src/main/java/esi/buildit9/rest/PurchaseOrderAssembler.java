package esi.buildit9.rest;

import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RentIt;
import esi.buildit9.domain.Site;
import esi.buildit9.rest.controller.PurchaseOrderRestController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PurchaseOrderAssembler extends ResourceAssemblerSupport<PurchaseOrder, PurchaseOrderResource> {

	private PurchaseOrderLineAssembler lineAssembler;

	public PurchaseOrderAssembler() {
        super(PurchaseOrderRestController.class, PurchaseOrderResource.class);
        lineAssembler = new PurchaseOrderLineAssembler();
	}

    public PurchaseOrderResource toResource(PurchaseOrder order) {
        PurchaseOrderResource res = new PurchaseOrderResource();
		res.setInternalId(order.getId());
		res.setOrderStatus(order.getOrderStatus());
        res.setBuildit("builtit9"); //TODO this must e changed.. someday
		res.setRentit(getName(order));
        res.setSiteAddress(getAddress(order));
        res.setOrderLines(lineAssembler.toResource(order.getLines()));
		res.setTotalPrice(order.getTotalPrice());
		res.setWorksEngineerName(order.getWorksEngineerName());
        return res;
    }

	private String getAddress(PurchaseOrder order) {
		if (order.getSite() != null) {
			return order.getSite().getAddress();
		}
		return "default";
	}

	private String getName(PurchaseOrder order) {
		if (order.getRentit() != null) {
			return order.getRentit().getName();
		}
		return "rentit9";
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

    public PurchaseOrder fromResource(PurchaseOrderResource res){
        PurchaseOrder order = new PurchaseOrder();
        order.setRentit(RentIt.getOrCreateRentIt(res.getRentit()));
        order.setSite(Site.getOrCreateSite(res.getSiteAddress()));
        order.setOrderStatus(res.getOrderStatus());
        order.setSiteEngineerName(res.getSiteEngineerName());
        order.setWorksEngineerName(res.getWorksEngineerName());
        return order;
    }

}
