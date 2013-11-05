package esi.buildit9.service;

import org.w3c.dom.Document;

import esi.buildit9.domain.Invoice;
import esi.buildit9.domain.InvoiceStatus;
import esi.buildit9.domain.PurchaseOrder;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class InvoiceHelper {

    public static InvoiceResource unmarshall(Document invoice) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(InvoiceResource.class);
            return  (InvoiceResource) ctx.createUnmarshaller().unmarshal(invoice);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public static String tryGetSender(Address[] senders) {
        for (Address sender : senders) {
            if (sender instanceof InternetAddress) {
                return ((InternetAddress) sender).getAddress();
            }
        }
        throw new IllegalArgumentException("not an InternetAddress");
    }
    
    public static void persist(PurchaseOrder order, String senderEmail, InvoiceStatus status) {
        Invoice invoice = new Invoice();
        invoice.setRentit(order.getRentit());
        invoice.setPurchaseOrder(order);
        invoice.setStatus(status);
        invoice.setSenderEmail(senderEmail);
        invoice.persist();
    }

}
