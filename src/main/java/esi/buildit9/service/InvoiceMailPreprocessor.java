package esi.buildit9.service;


import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Component
public class InvoiceMailPreprocessor {

    private final DocumentBuilder builder;

    public InvoiceMailPreprocessor() {
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @ServiceActivator
    public InvoiceSDO process(Message msg) throws IOException, MessagingException, SAXException {
        Multipart content = getMultipart(msg);
        for (int i = 0; i < content.getCount(); i++) {
            BodyPart part = content.getBodyPart(i);
            if (isInvoiceXml(part)) {
                Document document = builder.parse(part.getInputStream());
                return new InvoiceSDO(document, msg.getFrom());
            }
        }
        throw new IllegalArgumentException("message does not contain an invoice");
    }

    private static boolean isInvoiceXml(BodyPart part) throws MessagingException {
        String fileName = part.getFileName();
        return fileName != null && fileName.toLowerCase().contains("invoice");
    }

    private static Multipart getMultipart(Message msg) throws IOException, MessagingException {
        Object contentObject = msg.getContent();
        if (contentObject instanceof Multipart) {
            return (Multipart) contentObject;
        } else {
            throw new IllegalArgumentException("not a multipart message");
        }
    }

}
