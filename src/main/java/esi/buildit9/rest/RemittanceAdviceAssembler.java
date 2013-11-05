package esi.buildit9.rest;

import java.util.Calendar;

import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RemittanceAdvice;
import esi.buildit9.domain.RentIt;
import esi.buildit9.domain.Site;
import esi.buildit9.rest.RemittanceAdviceResource;
import esi.buildit9.rest.controller.PurchaseOrderRestController;
import esi.buildit9.rest.util.ExtendedLink;
import esi.buildit9.rest.util.MethodLookupHelper;

public class RemittanceAdviceAssembler {

    private final MethodLookupHelper linker;

    public RemittanceAdviceAssembler() {
    	linker = new MethodLookupHelper(PurchaseOrderRestController.class);
    }
    
    public RemittanceAdviceResource toResource(RemittanceAdvice advice) {
		RemittanceAdviceResource res = new RemittanceAdviceResource();
		addSelfLink(advice, res);
		res.setInvoice(advice.getInvoice());
		res.setPayDay(Calendar.getInstance());
    	return null;
		
	}
    
    private void addSelfLink(RemittanceAdvice advice, RemittanceAdviceResource res) {
        ExtendedLink getById = linker.buildLink(PurchaseOrderRestController.METHOD_GET_BY_ID, advice.getId());
        res.add(new ExtendedLink(getById.getHref(), "self", getById.getMethod()));
    }
    
    public RemittanceAdvice fromResource(RemittanceAdviceResource res){
        RemittanceAdvice remittanceAdvice = new RemittanceAdvice();
        remittanceAdvice.setInvoice(res.getInvoice());
        remittanceAdvice.setPayDay(res.getPayDay());
        return remittanceAdvice;
    }
}
