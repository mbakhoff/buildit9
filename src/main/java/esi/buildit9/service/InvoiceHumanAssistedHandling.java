package esi.buildit9.service;

import esi.buildit9.domain.Invoice;
import esi.buildit9.domain.InvoiceStatus;
import esi.buildit9.domain.PurchaseOrder;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
public class InvoiceHumanAssistedHandling {

    @ServiceActivator
    public void process(InvoiceSDO invoiceSDO) {
        InvoiceResource invoiceResource = InvoiceHelper.unmarshall(invoiceSDO.document);
        PurchaseOrder order = PurchaseOrder.findPurchaseOrder(invoiceResource.getPo());
        if (Math.abs(order.getTotalPrice() - invoiceResource.getTotal()) < 0.1) {
            PurchaseOrder order1 = PurchaseOrder.findPurchaseOrder(invoiceResource.getPo());
            Invoice invoice = new Invoice();
            invoice.setRentit(order1.getRentit());
            invoice.setPurchaseOrder(order1);
            invoice.setStatus(InvoiceStatus.PENDING);
            invoice.setSenderEmail(InvoiceHelper.tryGetSender(invoiceSDO.from));
            invoice.persist();
        } else {
            // TODO: send email
        }
    }

}
