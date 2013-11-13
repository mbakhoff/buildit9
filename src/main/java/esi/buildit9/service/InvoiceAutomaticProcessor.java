package esi.buildit9.service;

import esi.buildit9.domain.Invoice;
import esi.buildit9.domain.InvoiceStatus;
import esi.buildit9.domain.PurchaseOrder;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class InvoiceAutomaticProcessor {

    @ServiceActivator
    public MailMessage process(InvoiceSDO invoiceSDO) {
    	InvoiceResource invoiceResource = InvoiceHelper.unmarshall(invoiceSDO.document);
    	float documentTotal = invoiceResource.getTotal();
    	long documentPO = invoiceResource.getPo();
        PurchaseOrder purchaseOrder = PurchaseOrder.findPurchaseOrder(documentPO);
    	String address = InvoiceHelper.tryGetSender(invoiceSDO.from);
    	
    	// Check if such PO exists
    	if (purchaseOrder != null) {
            float poTotal = purchaseOrder.getTotalPrice();
    		// Check if Totals match
    		if (documentTotal == poTotal) {
                Invoice invoice = InvoiceHelper.persist(invoiceResource, purchaseOrder, address, InvoiceStatus.APPROVED);
                InvoiceHelper.createAndSendRemittanceAdvice(invoice);
                invoice.setStatus(InvoiceStatus.COMPLETED);
                invoice.persist();
                return sendEmail("Thank you for the invoice with PO" + documentPO + "!", address);
			}else {
				return sendEmail("Error with invoice - totals don't match!", address);
			}
		} else {
			return sendEmail("Error with invoice - no such PO id!", address);
		}
    }

    public MailMessage sendEmail(String text, String to){
    	MailMessage message = new SimpleMailMessage();
    	message.setTo(to);
    	message.setSubject("Builtit9 invoice reply");
    	message.setText(text);
    	return message;
    }
}