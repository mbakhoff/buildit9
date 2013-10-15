package esi.buildit9.rest;

import esi.buildit9.domain.PurchaseOrderLine;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderLineAssembler {

    public PurchaseOrderLineResource toResource(PurchaseOrderLine line){
       	PurchaseOrderLineResource res = new PurchaseOrderLineResource();
		res.setPlantId(line.getPlantExternalId());
		res.setStartDate(line.getStartDate());
		res.setEndDate(line.getEndDate());
		res.setTotalCost(line.getTotalCost());
		return res;
    }

    public List<PurchaseOrderLineResource> toResource(List<PurchaseOrderLine> lines){
        List<PurchaseOrderLineResource> all = new ArrayList<PurchaseOrderLineResource>();
		for (PurchaseOrderLine line : lines) {
			all.add(toResource(line));
		}
        return all;
    }

}
