package esi.buildit9.interop;

public enum InteropImplementation implements RentitInterop {

    Team9 {
        @Override
        public Rest getRest() {
            return new Team9Rest();
        }

        @Override
        public Soap getSoap() {
            throw new UnsupportedOperationException();
        }
    },

    Dummy {
        @Override
        public Rest getRest() {
            return new DummyRest();
        }

        @Override
        public Soap getSoap() {
            return new DummySoap();
        }
    }

}