package esi.buildit9.interop;

import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RemittanceAdvice;

import java.util.Calendar;
import java.util.List;

public interface RentitInterop {

    void submitOrder(PurchaseOrder order);
    void updateOrder(PurchaseOrder order);
    void extendOrder(PurchaseOrder order);
    void cancelOrder(PurchaseOrder order);
    void submitRemittanceAdvice(RemittanceAdvice remittanceAdvice);
    List<esi.buildit9.rest.PlantResource> getAvailablePlantsBetween(String nameLike, Calendar startDate, Calendar endDate);
    List<esi.buildit9.rest.PlantResource> getPlants();

}
