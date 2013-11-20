package esi.builtit9.service;

import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RentIt;
import esi.buildit9.interop.InteropImplementation;
import esi.buildit9.interop.RemoteHostException;
import esi.buildit9.service.InvoiceAutomaticProcessor;
import esi.buildit9.service.InvoiceHumanAssistedHandling;
import esi.buildit9.service.InvoiceResource;
import esi.buildit9.service.InvoiceSDO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class InvoicingTest {

    @Autowired
    private DirectChannel invoiceChannel;

    private final DocumentBuilder builder;
    private final Marshaller marshaller;

    private RentIt rentIt;

    public InvoicingTest() {
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            marshaller = JAXBContext.newInstance(InvoiceResource.class).createMarshaller();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setUp() throws Exception {
        rentIt = new RentIt();
        rentIt.setName("test rentit");
        rentIt.setProvider(InteropImplementation.Team9);
        rentIt.persist();
    }

    @After
    public void tearDown() throws Exception {
        rentIt.remove();
    }

    @Test
    public void testChannels() throws Exception {
        InvoiceResource invoice = new InvoiceResource();
        invoice.setPo(1L);
        invoice.setTotal(49.99f);
        invoice.setId(1L);
        invoiceChannel.send(createMessage(invoice));
    }

    @Test
    @Transactional
    public void testAutomatic() throws Exception {
        PurchaseOrder order = new PurchaseOrder();
        order.setRentit(rentIt);
        order.setTotalPrice(49.99f);
        order.persist();

        InvoiceResource invoice = new InvoiceResource();
        invoice.setPo(order.getId());
        invoice.setTotal(49.99f);
        invoice.setId(1L);

        try {
            new InvoiceAutomaticProcessor().process(createInvoiceSdo(invoice));
        } catch (RemoteHostException ignored) {
        }
    }

    @Test
    @Transactional
    public void testManual() throws Exception {
        PurchaseOrder order = new PurchaseOrder();
        order.setRentit(rentIt);
        order.setTotalPrice(199.99f);
        order.persist();

        InvoiceResource invoice = new InvoiceResource();
        invoice.setPo(order.getId());
        invoice.setTotal(199.99f);
        invoice.setId(1L);

        try {
            new InvoiceHumanAssistedHandling().process(createInvoiceSdo(invoice));
        } catch (RemoteHostException ignored) {
        }
    }

    private GenericMessage<InvoiceSDO> createMessage(InvoiceResource invoice) throws Exception {
        return new GenericMessage<InvoiceSDO>(createInvoiceSdo(invoice));
    }

    private InvoiceSDO createInvoiceSdo(InvoiceResource invoice) throws IOException, JAXBException, SAXException, AddressException {
        PipedOutputStream pipeOut = new PipedOutputStream();
        PipedInputStream pipeIn = new PipedInputStream(pipeOut);
        marshaller.marshal(invoice, pipeOut);
        pipeOut.close();
        Document document = builder.parse(pipeIn);
        pipeIn.close();
        Address[] from = new Address[] { new InternetAddress("rentit9esi@gmail.com") };
        return new InvoiceSDO(document, from);
    }

}
