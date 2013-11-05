package esi.builtit9.service;

import esi.buildit9.service.InvoiceResource;
import esi.buildit9.service.InvoiceSDO;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.message.GenericMessage;
import org.w3c.dom.Document;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class InvoicingTest {

    private ApplicationContext context;
    private DirectChannel invoiceChannel;
    private DocumentBuilder builder;
    private Marshaller marshaller;

    public InvoicingTest() {
        try {
            context = new ClassPathXmlApplicationContext("/META-INF/spring/applicationContext-InvoiceProcessing.xml");
            invoiceChannel = context.getBean("invoiceChannel", DirectChannel.class);
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            marshaller = JAXBContext.newInstance(InvoiceResource.class).createMarshaller();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAutomatic() throws Exception {
        InvoiceResource invoice = new InvoiceResource();
        invoice.setPo(1L);
        invoice.setTotal(49.99f);
        invoiceChannel.send(createMessage(invoice));
    }

    @Test
    public void testManual() throws Exception {
        InvoiceResource invoice = new InvoiceResource();
        invoice.setPo(1L);
        invoice.setTotal(199.99f);
        invoiceChannel.send(createMessage(invoice));
    }

    private GenericMessage<InvoiceSDO> createMessage(InvoiceResource invoice) throws Exception {
        PipedOutputStream pipeOut = new PipedOutputStream();
        PipedInputStream pipeIn = new PipedInputStream(pipeOut);
        marshaller.marshal(invoice, pipeOut);
        pipeOut.close();
        Document document = builder.parse(pipeIn);
        pipeIn.close();
        Address[] from = new Address[] { new InternetAddress("renit9esi@gmail.com") };
        return new GenericMessage<InvoiceSDO>(new InvoiceSDO(document, from));
    }

}
