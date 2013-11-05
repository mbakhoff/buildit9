package esi.buildit9.service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import esi.buildit9.domain.PurchaseOrder;

@Component
public class InvoiceAutomaticProcessor {

    @ServiceActivator
    public void process(InvoiceSDO invoiceSDO) {
    	InvoiceResource invoiceResource = InvoiceHelper.unmarshall(invoiceSDO.document);
    	float documentTotal = invoiceResource.getTotal();
    	long documentPO = invoiceResource.getPo();
    	float POTotal = PurchaseOrder.findPurchaseOrder(documentPO).getTotalPrice();

    	// Check if such PO exists
    	if (PurchaseOrder.findPurchaseOrder(documentPO) != null) {
    		// Check if Totals match
    		if (documentTotal == POTotal) {
    			// TODO
    			JavaMailSenderImpl sender = new JavaMailSenderImpl();
    			sender.setHost("mail.host.com");

    			MimeMessage message = sender.createMimeMessage();
    			MimeMessageHelper helper = new MimeMessageHelper(message);
    			try {
					helper.setTo(((InternetAddress) invoiceSDO.from[0]).getAddress());
	    			helper.setText("Thank you for the invoice with PO" + documentPO + "!");
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

    			sender.send(message);
			}
		}
    	
    	
        throw new UnsupportedOperationException();
    }
}
