package esi.buildit9.service;

import org.w3c.dom.Document;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class InvoiceRouter {

    public String analyzeInvoice(InvoiceSDO invoice) {
        if (getOrderTotal(invoice.document) <= 100) {
            return "MINOR";
        } else {
            return "MAJOR";
        }
    }

    private float getOrderTotal(Document invoice) {
        try {
            return Float.parseFloat(XPathFactory.newInstance().newXPath().evaluate("//total", invoice));
        } catch (XPathExpressionException e) {
            throw new RuntimeException("failed to parse total from incoming po xml", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("total in incoming po xml is not a valid float", e);
        }
    }

}
