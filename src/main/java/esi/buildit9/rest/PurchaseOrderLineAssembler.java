package esi.buildit9.rest;

import esi.buildit9.domain.PurchaseOrderLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PurchaseOrderLineAssembler {

    public PurchaseOrderLineResource toResource(PurchaseOrderLine line) {
        PurchaseOrderLineResource res = new PurchaseOrderLineResource();
        res.setPlantId(line.getPlantExternalId());
        res.setStartDate(line.getStartDate());
        res.setEndDate(line.getEndDate());
        res.setTotalCost(line.getTotalCost());
        return res;
    }

/*    public List<PurchaseOrderLineResource> toResource(Set<PurchaseOrderLine> lines){
        List<PurchaseOrderLineResource> all = new ArrayList<PurchaseOrderLineResource>();
		for (PurchaseOrderLine line : lines) {
			all.add(toResource(line));
		}
        return all;
    }*/

    public PurchaseOrderLineListResource toResource(Set<PurchaseOrderLine> lines) {
        List<PurchaseOrderLineResource> all = new ArrayList<PurchaseOrderLineResource>();
        for (PurchaseOrderLine line : lines) {
            all.add(toResource(line));
        }
        PurchaseOrderLineListResource resource = new PurchaseOrderLineListResource();

        resource.orderLines = all;

        return resource;
    }

}
