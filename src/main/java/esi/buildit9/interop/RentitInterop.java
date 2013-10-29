package esi.buildit9.interop;

import esi.buildit9.domain.PurchaseOrder;

import java.util.Calendar;
import java.util.List;

public interface RentitInterop {

    Rest getRest();
    Soap getSoap();

    public static interface Rest {
        void submitOrder(PurchaseOrder order);
        List<esi.buildit9.rest.PlantResource> getAvailablePlantsBetween(String nameLike, Calendar startDate, Calendar endDate);
    }

    public static interface Soap {
        List<esi.buildit9.soap.client.PlantResource> getAvailablePlantsBetween(String nameLike, Calendar startDate, Calendar endDate);
    }
}
