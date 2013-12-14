package esi.buildit9.interop;

import esi.buildit9.interop.Team1Interop;

public enum InteropImplementation {

    Team9 {
        @Override
        public RentitInterop getImpl() {
            return new Team9Interop();
        }
    },

    Team1 {
        @Override
        public RentitInterop getImpl() {
            return new Team1Interop();
        }
    },

    Dummy {
        @Override
        public RentitInterop getImpl() {
            return new DummyInterop();
        }
    };

    public abstract RentitInterop getImpl();

}
