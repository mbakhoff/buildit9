package esi.buildit9.interop;

import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.soap.client.PlantResource;

import java.util.Calendar;
import java.util.List;

public interface RentitInterop {

    Rest getRest();
    Soap getSoap();

    public static interface Rest {
        void submitOrder(PurchaseOrder order);
    }

    public static interface Soap {
        List<PlantResource> getAvailablePlantsBetween(String nameLike, Calendar startDate, Calendar endDate);
    }
}
