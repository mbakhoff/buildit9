package esi.builtit9.interop;

import esi.buildit9.interop.RentitInterop;
import esi.buildit9.interop.Team9Interop;
import esi.buildit9.rest.PlantResource;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class Team9RestTest {

    private RentitInterop rest;

    public Team9RestTest() {
        rest = new Team9Interop();
    }

    @Test
    public void testGetBetween() throws Exception {
        List<PlantResource> plants = rest.getAvailablePlantsBetween("Truck", Calendar.getInstance(), Calendar.getInstance());
        assertNotNull(plants);
    }

}
