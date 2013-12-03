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
        InvoiceResource invoiceResource = InvoiceHelper.unmarshall(invoiceSDO.document);
        String senderEmail = InvoiceHelper.tryGetSender(invoiceSDO.from);

        PurchaseOrder order = PurchaseOrder.findPurchaseOrder(invoiceResource.getPo());
        if (order != null) {
            if (Math.abs(order.getTotalPrice() - invoiceResource.getTotal()) >= 1f) {
                String message = "Total of PO " + order.getId() + " did not match. Expected " + order.getTotalPrice();
                return InvoiceHelper.createMessage(message, senderEmail);
            } else if (InvoiceHelper.hasExistingInvoiceForPayment(order)) {
                return InvoiceHelper.createMessage("Invoice already exists for order " + order.getId(), senderEmail);
            } else {
                InvoiceHelper.persist(invoiceResource, order, senderEmail, InvoiceStatus.PENDING);
                return InvoiceHelper.createMessage("Invoice for "+order.getId()+" received", senderEmail);
            }
        } else {
            return InvoiceHelper.createMessage("PO " + invoiceResource.getPo() + " was not found", senderEmail);
        }
    }

}
