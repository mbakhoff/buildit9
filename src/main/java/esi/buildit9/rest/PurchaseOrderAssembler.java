package esi.buildit9.rest;

import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RentIt;
import esi.buildit9.domain.Site;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderAssembler {

	private PurchaseOrderLineAssembler lineAssembler;

	public PurchaseOrderAssembler() {
		lineAssembler = new PurchaseOrderLineAssembler();
	}

	public PurchaseOrderResource toResource(PurchaseOrder order) {
		PurchaseOrderResource res = new PurchaseOrderResource();
		res.setBuildit("builtit9"); //TODO this must e changed.. someday
		res.setSiteAddress(order.getSite().getAddress());
		res.setPurchaseOrderLines(lineAssembler.toResource(order.getLines()));
		return res;
	}

	public List<PurchaseOrderResource> toResource(List<PurchaseOrder> orders) {
		List<PurchaseOrderResource> all = new ArrayList<PurchaseOrderResource>();
		for (PurchaseOrder order : orders) {
			all.add(toResource(order));
		}
		return all;
	}

    public PurchaseOrder fromResource(PurchaseOrderResource res){
        PurchaseOrder order = new PurchaseOrder();
        order.setRentit(getOrCreateRentIt(res.getRentit()));
        order.setSite(getOrCreateSite(res.getSiteAddress()));
        order.setOrderStatus(res.getOrderStatus());
        order.setSiteEngineerName(res.getSiteEngineerName());
        order.setWorksEngineerName(res.getWorksEngineerName());
        return order;
    }

    private RentIt getOrCreateRentIt(String rentit) {
        RentIt rentit1 = RentIt.findRentItsByNameEquals(rentit).getSingleResult();
        //rentit1.setName(rentit);
        //TODO implement findRentItByName
        return rentit1;
    }

    public Site getOrCreateSite(String siteAddress) {
        Site site1=Site.findSitesByAddressEquals(siteAddress).getSingleResult();
        //site1.setAddress(siteAddress);

        //TODO implement findSiteAddress
        return site1; //To change body of created methods use File | Settings | File Templates.
    }

}
