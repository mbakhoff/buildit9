package esi.buildit9.service;

import esi.buildit9.domain.InvoiceStatus;
import esi.buildit9.domain.PurchaseOrder;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.MailMessage;
import org.springframework.stereotype.Component;

@Component
public class InvoiceHumanAssistedHandling {

    @ServiceActivator
    public MailMessage process(InvoiceSDO invoiceSDO) {
        String senderEmail = invoiceSDO.rentIt.getEmail();

        PurchaseOrder order = PurchaseOrder.findPurchaseOrder(invoiceSDO.po);
        if (order != null) {
            if (Math.abs(order.getTotalPrice() - invoiceSDO.total) >= 1f) {
                String message = "Total of PO " + order.getId() + " did not match. Expected " + order.getTotalPrice();
                System.err.printf("total of invoice %d didnt match%n", invoiceSDO.id);
                return InvoiceHelper.createMessage(message, senderEmail);
            } else if (InvoiceHelper.hasExistingInvoiceForPayment(order)) {
                System.err.printf("invoice already exists for po %d%n", invoiceSDO.po);
                return InvoiceHelper.createMessage("Invoice already exists for order " + order.getId(), senderEmail);
            } else {
                InvoiceHelper.persist(invoiceSDO, order, senderEmail, InvoiceStatus.PENDING);
                System.err.println("persisted invoice");
                return InvoiceHelper.createMessage("Invoice for "+order.getId()+" received", senderEmail);
            }
        } else {
            return InvoiceHelper.createMessage("PO " + invoiceSDO.po + " was not found", senderEmail);
        }
    }

}
