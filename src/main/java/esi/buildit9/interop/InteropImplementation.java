package esi.buildit9.interop;

public enum InteropImplementation implements RentitInterop {

    Team9 {
        @Override
        public Rest getRest() {
            return new Team9Rest();
        }
    },

    Dummy {
        @Override
        public Rest getRest() {
            return new DummyRest();
        }
    }

}
