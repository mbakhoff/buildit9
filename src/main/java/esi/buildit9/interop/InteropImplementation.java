package esi.buildit9.interop;

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

    Team30 {
        @Override
        public RentitInterop getImpl() {
            return new Team30Interop();
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
