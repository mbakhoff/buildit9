package esi.buildit9.interop;

import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RemittanceAdvice;
import esi.buildit9.rest.PlantResource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DummyRest implements RentitInterop.Rest {
    @Override
    public void submitOrder(PurchaseOrder order) {
        System.out.println(DummyRest.class.getCanonicalName() + " dummy submit");
    }

    @Override
    public List<PlantResource> getAvailablePlantsBetween(String nameLike, Calendar startDate, Calendar endDate) {
        return new ArrayList<PlantResource>();
    }

	@Override
	public void submitRemittanceAdvice(RemittanceAdvice remittanceAdvice) {
		System.out.println(DummyRest.class.getCanonicalName() + " remittanceAdvice submit");		
	}
}
