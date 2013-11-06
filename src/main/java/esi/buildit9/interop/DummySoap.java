package esi.buildit9.interop;

import esi.buildit9.soap.client.PlantResource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DummySoap implements RentitInterop.Soap {
    @Override
    public List<PlantResource> getAvailablePlantsBetween(String nameLike, Calendar startDate, Calendar endDate) {
        return new ArrayList<PlantResource>();
    }
}
