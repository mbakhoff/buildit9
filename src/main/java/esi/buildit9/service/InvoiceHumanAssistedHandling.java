package esi.buildit9.service;

import esi.buildit9.domain.InvoiceStatus;
import esi.buildit9.domain.PurchaseOrder;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class InvoiceHumanAssistedHandling {

    @ServiceActivator
    public MailMessage process(InvoiceSDO invoiceSDO) {
        InvoiceResource invoiceResource = InvoiceHelper.unmarshall(invoiceSDO.document);
        String senderEmail = InvoiceHelper.tryGetSender(invoiceSDO.from);

        PurchaseOrder order = PurchaseOrder.findPurchaseOrder(invoiceResource.getPo());
        if (order != null) {
            if (Math.abs(order.getTotalPrice() - invoiceResource.getTotal()) < 0.1) {
                InvoiceHelper.persist(invoiceResource, order, senderEmail, InvoiceStatus.PENDING);
                return null;
            } else {
                return totalDidntMatch(senderEmail, order);
            }
        } else {
            return noSuchOrder(senderEmail, invoiceResource.getPo());
        }
    }

    private MailMessage noSuchOrder(String senderEmail, long id) {
        MailMessage message = new SimpleMailMessage();
        message.setTo(senderEmail);
        message.setSubject("Invoice auto-rejected");
        message.setText("PO " + id + " was not found");
        return message;
    }

    private MailMessage totalDidntMatch(String senderEmail, PurchaseOrder order) {
        MailMessage message = new SimpleMailMessage();
        message.setTo(senderEmail);
        message.setSubject("Invoice auto-rejected");
        message.setText("Total of PO " + order.getId() + " did not match. Expected " + order.getTotalPrice());
        return message;
    }

}