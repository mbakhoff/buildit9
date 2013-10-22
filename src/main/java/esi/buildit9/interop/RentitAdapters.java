package esi.buildit9.interop;

public enum RentitAdapters implements RentitAdapterProvider {

    Team9 {
        @Override
        public RestAdapter getRest() {
            return new Team9Rest();
        }
    },

    Default {
        @Override
        public RestAdapter getRest() {
            return new DummyRest();
        }
    }

}
