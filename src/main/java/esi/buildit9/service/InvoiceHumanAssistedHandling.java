package esi.buildit9.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.mail.Message;

@Component
public class InvoiceHumanAssistedHandling {

    @ServiceActivator
    public Document process(Message msg) {
        throw new UnsupportedOperationException();
    }

}
