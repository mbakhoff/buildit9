package esi.builtit9.rest;

import esi.buildit9.soap.client.*;
import org.joda.time.DateMidnight;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext-ws.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class Practice7 {

    @Autowired
    private PlantSoapService service;
    
    private DatatypeFactory datatypeFactory;

    public Practice7() throws DatatypeConfigurationException {
        datatypeFactory = DatatypeFactory.newInstance();
    }

    @Test
    public void testGetAllSoapPlants() throws Exception {
        List<PlantResource> plants = service.getAllPlants();
        assertNotNull(plants);
    }

    @Test
    public void testGetSoapPlantsBetweenDates() throws Exception {
        PlantsAvailableRequest spec = new PlantsAvailableRequest();
        spec.setNameLike("Dodge");
        spec.setStartDate(create(new DateMidnight(2013, 10, 23)));
        spec.setEndDate(create(new DateMidnight(2013, 10, 29)));

        List<PlantResource> plants = service.getPlantsBetween(spec);
        assertNotNull(plants);
    }

    @Test
    public void testCreateOrder() throws Exception {
        PurchaseOrderResource orderResource = createRandomOrder();
        PurchaseOrderResource echo = service.createPurchaseOrder(orderResource);
        assertNotNull(echo);
        assertNotNull(echo.getInternalId());
        assertEquals(orderResource.getSiteAddress(), echo.getSiteAddress());
    }

    private PurchaseOrderResource createRandomOrder() {
        PurchaseOrderLineResource lineResource = new PurchaseOrderLineResource();
        lineResource.setPlantId("1");
        lineResource.setTotalPrice(100f);
        lineResource.setStartDate(create(new DateMidnight(2013, 10, 23)));
        lineResource.setEndDate(create(new DateMidnight(2013, 10, 29)));

        PurchaseOrderResource orderResource = new PurchaseOrderResource();
        orderResource.setInternalId(randomId());
        orderResource.setSiteAddress("siteaddr-test");
        orderResource.setBuildit("buildit9");
        orderResource.getPurchaseOrderLines().add(lineResource);
        return orderResource;
    }

    private Long randomId() {
        return (long)(Math.random()*1000);
    }

    private XMLGregorianCalendar create(DateMidnight dateMidnight) {
        // yeah, uh.. wat 
        return datatypeFactory.newXMLGregorianCalendar(dateMidnight.toGregorianCalendar());
    }
}
