package esi.builtit9.interop;

import esi.buildit9.interop.InteropImplementation;
import esi.buildit9.interop.RentitInterop;
import esi.buildit9.rest.PlantResource;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class Team9RestTest {

    private RentitInterop.Rest rest;

    public Team9RestTest() {
        rest = InteropImplementation.Team9.getRest();
    }

    @Test
    public void testGetBetween() throws Exception {
        List<PlantResource> plants = rest.getAvailablePlantsBetween("Truck", Calendar.getInstance(), Calendar.getInstance());
        assertNotNull(plants);
    }

}
