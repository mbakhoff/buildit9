package esi.buildit9.service;

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

}
