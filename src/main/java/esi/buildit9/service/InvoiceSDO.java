package esi.buildit9.service;

import esi.buildit9.domain.RentIt;

public class InvoiceSDO {

    public final RentIt rentIt;
    public final Long id;
    public final Long po;
    public final Float total;

    public InvoiceSDO(RentIt rentIt, Long id, Long po, Float total) {
        this.rentIt = rentIt;
        this.id = id;
        this.po = po;
        this.total = total;
    }

}
