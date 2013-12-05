package esi.buildit9.interop;

import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RemittanceAdvice;

import java.util.Calendar;
import java.util.List;

public interface RentitInterop {

    Rest getRest();
    Soap getSoap();

    public static interface Rest {
        void submitOrder(PurchaseOrder order);
        void submitRemittanceAdvice(RemittanceAdvice remittanceAdvice);
        List<esi.buildit9.rest.PlantResource> getAvailablePlantsBetween(String nameLike, Calendar startDate, Calendar endDate);
        List<esi.buildit9.rest.PlantResource> getPlants();
    }

    public static interface Soap {
        List<esi.buildit9.soap.client.PlantResource> getAvailablePlantsBetween(String nameLike, Calendar startDate, Calendar endDate);
    }
}
