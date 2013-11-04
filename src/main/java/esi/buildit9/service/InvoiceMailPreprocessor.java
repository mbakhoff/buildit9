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
    public Document process(Message msg) throws MessagingException, IOException, ParserConfigurationException, SAXException {
        Multipart content = getMultipart(msg);
        for (int i = 0; i < content.getCount(); i++) {
            BodyPart part = content.getBodyPart(i);
            if (isXml(part.getContentType()) && part.getFileName().startsWith("invoice")) {
                return builder.parse(part.getInputStream());
            }
        }
        throw new RuntimeException("invoice not attached");
    }

    private static boolean isXml(String contentType) throws MessagingException {
        return contentType.startsWith("text/xml") || contentType.startsWith("application/xml");
    }

    private static Multipart getMultipart(Message msg) throws IOException, MessagingException {
        Object contentObject = msg.getContent();
        if (contentObject instanceof Multipart) {
            return (Multipart) contentObject;
        } else {
            throw new RuntimeException("Not a multipart message. Check if attachement attached");
        }
    }

}
