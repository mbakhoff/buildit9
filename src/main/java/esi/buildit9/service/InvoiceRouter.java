package esi.buildit9.service;

public class InvoiceRouter {

    public String analyzeInvoice(InvoiceSDO invoice) {
        if (invoice.total <= 100) {
            return "MINOR";
        } else {
            return "MAJOR";
        }
    }

}
