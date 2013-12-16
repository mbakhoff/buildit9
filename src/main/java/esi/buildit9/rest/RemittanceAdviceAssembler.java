package esi.buildit9.rest;

import esi.buildit9.domain.RemittanceAdvice;

import java.util.Calendar;

public class RemittanceAdviceAssembler {
    
    public RemittanceAdviceResource toResource(RemittanceAdvice advice) {
		RemittanceAdviceResource res = new RemittanceAdviceResource();
		res.setRentitInvoiceId(advice.getInvoice().getIdAtRentit());
		res.setPaymentDate(Calendar.getInstance());
    	return res;
	}

}
