package esi.buildit9.interop;

import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RemittanceAdvice;
import esi.buildit9.rest.PlantResource;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kmetsalu on 14/12/13.
 */
public class Team30Interop implements RentitInterop {
    @Override
    public void submitOrder(PurchaseOrder order) {

    }

    @Override
    public void updateOrder(PurchaseOrder order) {

    }

    @Override
    public void extendOrder(PurchaseOrder order) {

    }

    @Override
    public void cancelOrder(PurchaseOrder order) {

    }

    @Override
    public void submitRemittanceAdvice(RemittanceAdvice remittanceAdvice) {

    }

    @Override
    public List<PlantResource> getAvailablePlantsBetween(String nameLike, Calendar startDate, Calendar endDate) {
        return null;
    }

    @Override
    public List<PlantResource> getPlants() {

        return null;
    }
}
