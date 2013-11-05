package esi.buildit9.service;

import java.util.Calendar;
import java.util.Date;

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

    public static void createAndSendRemittanceAdvice(Invoice invoice) {
    	RemittanceAdvice remittanceAdvice = new RemittanceAdvice();
    	remittanceAdvice.setInvoice(invoice);
    	remittanceAdvice.setPayDay(Calendar.getInstance());
    	remittanceAdvice.persist();
    	invoice.getRentit().getProvider().getRest().submitRemittanceAdvice(remittanceAdvice);
    }

}
