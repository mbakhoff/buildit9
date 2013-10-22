package esi.buildit9.interop;

import esi.buildit9.domain.PurchaseOrder;

public interface RentitAdapterProvider {

    RestAdapter getRest();

    public interface RestAdapter {
        void submitOrder(PurchaseOrder order);
    }

    public static class InteropFailure extends RuntimeException {
        public InteropFailure() {
        }

        public InteropFailure(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
