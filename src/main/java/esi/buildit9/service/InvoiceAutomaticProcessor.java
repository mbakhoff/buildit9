package esi.buildit9.service;

import esi.buildit9.domain.Invoice;
import esi.buildit9.domain.InvoiceStatus;
import esi.buildit9.domain.PurchaseOrder;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.MailMessage;
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

    	if (purchaseOrder != null) {
            float poTotal = purchaseOrder.getTotalPrice();
            if (documentTotal != poTotal) {
                return InvoiceHelper.createMessage("Error with invoice - totals don't match!", address);
            } else if (InvoiceHelper.hasExistingInvoiceForPayment(purchaseOrder)) {
                return InvoiceHelper.createMessage("Purchase order " + documentPO + " already accepted or completed!", address);
            } else {
                Invoice invoice = InvoiceHelper.persist(invoiceResource, purchaseOrder, address, InvoiceStatus.APPROVED);
                InvoiceHelper.createAndSendRemittanceAdvice(invoice);
                invoice.setStatus(InvoiceStatus.COMPLETED);
                invoice.persist();
                return InvoiceHelper.createMessage("Thank you for the invoice with PO" + documentPO + "!", address);
            }
        } else {
			return InvoiceHelper.createMessage("Error with invoice - no such PO id!", address);
		}
    }

}
