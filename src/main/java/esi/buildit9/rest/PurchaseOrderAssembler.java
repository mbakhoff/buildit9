package esi.buildit9.rest;

import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RentIt;
import esi.buildit9.domain.Site;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PurchaseOrderAssembler {

    public static final String BUILDIT9 = "buildit9";

    public PurchaseOrderResource toResource(PurchaseOrder order) {
        PurchaseOrderResource res = new PurchaseOrderResource();
        res.setBuilditOrderId(order.getId().toString());
		res.setStatus(order.getOrderStatus().toString());
        res.setPlantId(order.getPlantExternalId());
        res.setPlantName(order.getPlantName());
        res.setBuildit(BUILDIT9);
		res.setRentit(getRentitName(order));
        res.setSiteAddress(getAddress(order));
        res.setTotal(order.getTotalPrice());
        res.setStartDate(order.getStartDate());
        res.setEndDate(order.getEndDate());
        res.setSiteEngineerName(order.getSiteEngineerName());
        res.setWorksEngineerName(order.getWorksEngineerName());
        return res;
    }

    private String getAddress(PurchaseOrder order) {
		if (order.getSite() != null) {
			return order.getSite().getAddress();
		}
		return "default";
	}

	private String getRentitName(PurchaseOrder order) {
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

    public PurchaseOrder fromResource(PurchaseOrder order, PurchaseOrderResource res) {
        order.setRentit(RentIt.getOrCreateRentIt(res.getRentit()));
        order.setSite(Site.getOrCreateSite(res.getSiteAddress()));
        order.setPlantName(res.getPlantName());
        order.setPlantExternalId(res.getPlantId());
        order.setTotalPrice(res.getTotal());
        order.setStartDate(res.getStartDate());
        order.setEndDate(res.getEndDate());
        order.setSiteEngineerName(res.getSiteEngineerName());
        order.setWorksEngineerName(res.getWorksEngineerName());
        return order;
    }

}
