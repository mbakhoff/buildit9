package esi.buildit9.service;

import esi.buildit9.domain.Invoice;
import esi.buildit9.domain.InvoiceStatus;
import esi.buildit9.domain.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.MailMessage;
import org.springframework.stereotype.Component;

@Component
public class InvoiceAutomaticProcessor {

    @Autowired
    private ApplicationContext ctx;

    @ServiceActivator
    public MailMessage process(InvoiceSDO invoiceSDO) {
        PurchaseOrder purchaseOrder = PurchaseOrder.findPurchaseOrder(invoiceSDO.po);
    	String address = invoiceSDO.rentIt.getEmail();
    	if (purchaseOrder != null) {
            float poTotal = purchaseOrder.getTotalPrice();
            if (invoiceSDO.total != poTotal) {
                return InvoiceHelper.createMessage("Error with invoice - totals don't match!", address);
            } else if (InvoiceHelper.hasExistingInvoiceForPayment(purchaseOrder)) {
                return InvoiceHelper.createMessage("Purchase order " + invoiceSDO.po + " already accepted or completed!", address);
            } else {
                Invoice invoice = InvoiceHelper.persist(invoiceSDO, purchaseOrder, address, InvoiceStatus.COMPLETED);
                InvoiceHelper.createAndSendRemittanceAdvice(ctx, invoice);
                return InvoiceHelper.createMessage("Thank you for the invoice with PO" + invoiceSDO.po + "!", address);
            }
        } else {
			return InvoiceHelper.createMessage("Error with invoice - no such PO id!", address);
		}
    }

}
