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

@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class InvoicingTest {

    @Autowired
    private DirectChannel invoiceChannel;

    private RentIt rentIt;

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
        invoiceChannel.send(new GenericMessage<>(
                new InvoiceSDO(rentIt, invoice.getId(), invoice.getPo(), invoice.getTotal())));
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
            new InvoiceAutomaticProcessor().process(new InvoiceSDO(rentIt, invoice.getId(), invoice.getPo(), invoice.getTotal()));
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
            new InvoiceHumanAssistedHandling().process(new InvoiceSDO(rentIt, invoice.getId(), invoice.getPo(), invoice.getTotal()));
        } catch (RemoteHostException ignored) {
        }
    }

}
