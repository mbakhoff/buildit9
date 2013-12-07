package esi.builtit9.interop;

import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.Site;
import esi.buildit9.interop.RentitInterop;
import esi.buildit9.interop.rentit1.Team1Interop;
import esi.buildit9.rest.PlantResource;
import org.joda.time.DateMidnight;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.List;

@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class Team1RestTest {

    private final RentitInterop api = new Team1Interop();

    @Test
    public void testGetAllPlants() throws Exception {
        List<PlantResource> plants = api.getPlants();
        Assert.assertNotNull(plants);
        for (PlantResource plant : plants) {
            System.out.println(plant.getId() + ", " + plant.getName());
        }
        System.out.println("received " + plants.size() + " plants");
    }

    @Test
    public void testPlantsByQuery() throws Exception {
        List<PlantResource> plants = api.getAvailablePlantsBetween("Hyundai",
                Calendar.getInstance(),
                new DateMidnight().plusDays(3).toGregorianCalendar());
        Assert.assertNotNull(plants);
        for (PlantResource plant : plants) {
            System.out.println(plant.getId() + ", " + plant.getName());
        }
        System.out.println("received " + plants.size() + " plants");
    }

    @Test
    public void testPoSubmission() throws Exception {
        PurchaseOrder order =  new PurchaseOrder();
        try {
            List<PlantResource> plants = api.getPlants();
            if (plants.size() > 0) {
                PlantResource plant = plants.get(0);
                Calendar start = Calendar.getInstance();
                Calendar end = new DateMidnight().plusDays(3).toGregorianCalendar();

                order.setPlantExternalId(plant.getId().toString());
                order.setSite(Site.getOrCreateSite("derpland construction site"));
                order.setSiteEngineerName("site engineer name");
                order.setWorksEngineerName("works engineer name");
                order.setStartDate(start);
                order.setEndDate(end);
                order.setTotalPrice(3 * plant.getPrice());
                order.persist();

                api.submitOrder(order);
                Assert.assertNotNull(order.getIdAtRentit());
            }
        } finally {
            order.remove();
        }
    }

}
