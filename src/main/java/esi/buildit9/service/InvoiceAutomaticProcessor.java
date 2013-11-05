package esi.buildit9.service;

import javax.mail.internet.MimeMessage;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import esi.buildit9.domain.PurchaseOrder;

@Component
public class InvoiceAutomaticProcessor {

    @ServiceActivator
    public void process(InvoiceSDO invoiceSDO) {
    	
    	float documentTotal = Float.parseFloat(getOrderTotal(invoiceSDO.document, "//total"));
    	long documentPOId = Long.parseLong(getOrderTotal(invoiceSDO.document, "//po"));
    	float POTotal = PurchaseOrder.findPurchaseOrder(documentPOId).getTotalPrice();

    	// Check if such PO exists
    	if (PurchaseOrder.findPurchaseOrder(documentPOId) != null) {
    		// Check if Totals match
    		if (documentPOId == POTotal) {
    			// TODO
			}
		}
    	
    	
        throw new UnsupportedOperationException();
    }
    
    private String getOrderTotal(Document invoiceSDODocument, String element) {
        try {
            return XPathFactory.newInstance().newXPath().evaluate(element, invoiceSDODocument);
        } catch (XPathExpressionException e) {
            throw new RuntimeException("failed to parse " + element + " from incoming po xml", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(element + "in incoming po xml is not a valid", e);
        }
    }

}
