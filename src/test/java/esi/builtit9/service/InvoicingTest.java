package esi.builtit9.service;

import esi.buildit9.service.InvoiceResource;
import esi.buildit9.service.InvoiceSDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class InvoicingTest {

    @Autowired
    private DirectChannel invoiceChannel;

    private final DocumentBuilder builder;
    private final Marshaller marshaller;

    public InvoicingTest() {
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            marshaller = JAXBContext.newInstance(InvoiceResource.class).createMarshaller();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAutomatic() throws Exception {
        InvoiceResource invoice = new InvoiceResource();
        invoice.setPo(1L);
        invoice.setTotal(49.99f);
        invoice.setId(1L);
        invoiceChannel.send(createMessage(invoice));
    }

    @Test
    public void testManual() throws Exception {
        InvoiceResource invoice = new InvoiceResource();
        invoice.setPo(1L);
        invoice.setTotal(199.99f);
        invoice.setId(1L);
        invoiceChannel.send(createMessage(invoice));
    }

    private GenericMessage<InvoiceSDO> createMessage(InvoiceResource invoice) throws Exception {
        PipedOutputStream pipeOut = new PipedOutputStream();
        PipedInputStream pipeIn = new PipedInputStream(pipeOut);
        marshaller.marshal(invoice, pipeOut);
        pipeOut.close();
        Document document = builder.parse(pipeIn);
        pipeIn.close();
        Address[] from = new Address[] { new InternetAddress("rentit9esi@gmail.com") };
        return new GenericMessage<InvoiceSDO>(new InvoiceSDO(document, from));
    }

}
