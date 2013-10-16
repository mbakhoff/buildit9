package esi.buildit9.rest;

import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RentIt;
import esi.buildit9.domain.Site;
import org.springframework.dao.DataRetrievalFailureException;

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

    private RentIt getOrCreateRentIt(String name) {
        RentIt rentIt;
        try{
            rentIt = RentIt.findRentItsByNameEquals(name).getSingleResult();
        }
        catch (DataRetrievalFailureException ee){
            rentIt=new RentIt();
            rentIt.setName(name);
            rentIt.persist();

        }
        return rentIt;
    }

    private Site getOrCreateSite(String siteAddress) {
        Site site;
        try{
            site = Site.findSitesByAddressEquals(siteAddress).getSingleResult();//.findRentItsByNameEquals(name).getSingleResult();
        }
        catch (DataRetrievalFailureException ee){
            site=new Site();
            site.setAddress(siteAddress);
            site.persist();

        }
        return site;
        //Site site1=Site.findSitesByAddressEquals(siteAddress).getSingleResult();
        //site1.setAddress(siteAddress);

        //TODO implement findSiteAddress
        //return site; //To change body of created methods use File | Settings | File Templates.
    }

}
