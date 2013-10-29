package esi.buildit9.interop;

import esi.buildit9.domain.PurchaseOrder;

public interface RentitInterop {

    Rest getRest();

    public static interface Rest {
        void submitOrder(PurchaseOrder order);
    }
}
