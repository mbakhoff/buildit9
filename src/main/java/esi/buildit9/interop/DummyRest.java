package esi.buildit9.interop;

import esi.buildit9.domain.PurchaseOrder;

public class DummyRest implements RentitInterop.Rest {
    @Override
    public void submitOrder(PurchaseOrder order) {
        System.out.println(DummyRest.class.getCanonicalName() + " dummy submit");
    }
}
