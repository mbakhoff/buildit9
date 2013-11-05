package esi.buildit9.service;

import esi.buildit9.domain.Invoice;
import esi.buildit9.domain.InvoiceStatus;
import esi.buildit9.domain.PurchaseOrder;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class InvoiceHelper {

    public static Invoice storeInvoice(InvoiceResource invoiceResource, InvoiceStatus status) {
        PurchaseOrder order = PurchaseOrder.findPurchaseOrder(invoiceResource.getPo());
        Invoice invoice = new Invoice();
        invoice.setRentit(order.getRentit());
        invoice.setPurchaseOrder(order);
        invoice.setStatus(status);
        invoice.persist();
        return invoice;
    }

    public static InvoiceResource unmarshall(Document invoice) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(InvoiceResource.class);
            return  (InvoiceResource) ctx.createUnmarshaller().unmarshal(invoice);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
