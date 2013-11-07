package esi.buildit9.rest;

import esi.buildit9.domain.RemittanceAdvice;
import esi.buildit9.rest.controller.PurchaseOrderRestController;
import esi.buildit9.rest.util.ExtendedLink;
import esi.buildit9.rest.util.MethodLookupHelper;

import java.util.Calendar;

public class RemittanceAdviceAssembler {

    private final MethodLookupHelper linker;

    public RemittanceAdviceAssembler() {
    	linker = new MethodLookupHelper(PurchaseOrderRestController.class);
    }
    
    public RemittanceAdviceResource toResource(RemittanceAdvice advice) {
		RemittanceAdviceResource res = new RemittanceAdviceResource();
		addSelfLink(advice, res);
		res.setInvoiceId(advice.getInvoice().getIdAtRentit());
		res.setPayDay(Calendar.getInstance());
    	return null;
		
	}
    
    private void addSelfLink(RemittanceAdvice advice, RemittanceAdviceResource res) {
        ExtendedLink getById = linker.buildLink(PurchaseOrderRestController.METHOD_GET_BY_ID, advice.getId());
        res.add(new ExtendedLink(getById.getHref(), "self", getById.getMethod()));
    }

}
