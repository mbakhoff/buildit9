package esi.buildit9.rest;

import esi.buildit9.domain.RemittanceAdvice;

import java.util.Calendar;

public class RemittanceAdviceAssembler {
    
    public RemittanceAdviceResource toResource(RemittanceAdvice advice) {
		RemittanceAdviceResource res = new RemittanceAdviceResource();
		res.setInvoiceId(advice.getInvoice().getIdAtRentit());
		res.setPayDay(Calendar.getInstance());
    	return res;
	}

}
