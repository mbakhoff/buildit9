package esi.buildit9.interop;

import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RemittanceAdvice;
import esi.buildit9.domain.RentIt;
import esi.buildit9.rest.PlantResource;
import esi.buildit9.service.InvoiceSDO;
import org.springframework.context.ApplicationContext;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DummyInterop implements RentitInterop {
    @Override
    public void submitOrder(PurchaseOrder order) {
        log("order submitted");
    }

    @Override
    public void updateOrder(PurchaseOrder order) {
        log("order updated");
    }

    @Override
    public void extendOrder(PurchaseOrder order) {
        log("order extended");
    }

    @Override
    public void cancelOrder(PurchaseOrder order) {
        log("order cancelled");
    }

    @Override
    public List<PlantResource> getAvailablePlantsBetween(String nameLike, Calendar startDate, Calendar endDate) {
        log("returning plants");
        return new ArrayList<PlantResource>();
    }

    @Override
    public List<PlantResource> getPlants() {
        log("returning plants");
        return new ArrayList<PlantResource>();
    }

    @Override
    public InvoiceSDO parseInvoice(RentIt rentIt, Document document) {
        log("not parsing invoice using default resource");
        return null;
    }

    @Override
	public void submitRemittanceAdvice(ApplicationContext ctx, RemittanceAdvice remittanceAdvice) {
		log("remittanceAdvice submit");
	}

    private static void log(String message) {
        System.out.println(DummyInterop.class.getCanonicalName() + " " + message);
    }
}
