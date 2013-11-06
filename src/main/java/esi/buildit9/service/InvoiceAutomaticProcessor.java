package esi.buildit9.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
public class InvoiceAutomaticProcessor {

    @ServiceActivator
    public void process(InvoiceSDO invoiceSDO) {
        throw new UnsupportedOperationException();
    }

}
