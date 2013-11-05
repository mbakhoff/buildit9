package esi.buildit9.service;

import org.w3c.dom.Document;

import javax.mail.Address;

public class InvoiceSDO {

    public final Document document;
    public final Address[] from;

    public InvoiceSDO(Document document, Address[] from) {
        this.document = document;
        this.from = from;
    }
}
