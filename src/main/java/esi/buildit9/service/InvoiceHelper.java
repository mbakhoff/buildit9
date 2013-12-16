package esi.buildit9.service;

import esi.buildit9.domain.Invoice;
import esi.buildit9.domain.InvoiceStatus;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RemittanceAdvice;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.w3c.dom.Document;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.Calendar;
import java.util.List;

public class InvoiceHelper {

    public static <T> T unmarshall(Document invoice, Class<T> resourceClass) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(resourceClass);
            return resourceClass.cast(ctx.createUnmarshaller().unmarshal(invoice));
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

    public static Invoice persist(InvoiceSDO invoiceResource, PurchaseOrder purchaseOrder, String address, InvoiceStatus status) {
        if (!invoiceResource.po.equals(purchaseOrder.getId())) {
            throw new IllegalArgumentException("PO id mismatch");
        }
        Invoice invoice = new Invoice();
        invoice.setRentit(purchaseOrder.getRentit());
        invoice.setPurchaseOrder(purchaseOrder);
        invoice.setStatus(status);
        invoice.setSenderEmail(address);
        invoice.setIdAtRentit(invoiceResource.id);
        invoice.persist();
        return invoice;
    }

    public static void sendManuallyApproved(ApplicationContext context, Invoice submitted) {
        MailMessage message = new SimpleMailMessage();
        message.setTo(submitted.getSenderEmail());
        message.setSubject("Invoice approved");
        message.setText("Invoice for " + submitted.getPurchaseOrder().getId() + " was approved by user");
        new OutgoingChannel(context).send(message);
    }

    public static void sendManuallyRejected(ApplicationContext context, Invoice submitted) {
        MailMessage message = new SimpleMailMessage();
        message.setTo(submitted.getSenderEmail());
        message.setSubject("Invoice manually rejected");
        message.setText("Invoice for " + submitted.getPurchaseOrder().getId() + " was rejected by user");
        new OutgoingChannel(context).send(message);
    }

    public static void createAndSendRemittanceAdvice(ApplicationContext ctx, Invoice invoice) {
    	RemittanceAdvice remittanceAdvice = new RemittanceAdvice();
    	remittanceAdvice.setInvoice(invoice);
    	remittanceAdvice.setPayDay(Calendar.getInstance());
    	remittanceAdvice.persist();
    	invoice.getRentit().getInterop().submitRemittanceAdvice(ctx, remittanceAdvice);
        invoice.setStatus(InvoiceStatus.COMPLETED);
        invoice.persist();
    }

    public static boolean hasExistingInvoiceForPayment(PurchaseOrder purchaseOrder) {
        List<Invoice> invoices = Invoice.findInvoicesByPurchaseOrder(purchaseOrder).getResultList();
        for (Invoice invoice : invoices) {
            if (invoice.isMarkedForPayment()) {
                return true;
            }
        }
        return false;
    }

    public static MailMessage createMessage(String text, String to) {
    	MailMessage message = new SimpleMailMessage();
    	message.setTo(to);
    	message.setSubject("Builtit9 invoice reply");
    	message.setText(text);
    	return message;
    }
}
