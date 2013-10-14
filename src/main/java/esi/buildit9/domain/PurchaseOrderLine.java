package esi.buildit9.domain;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class PurchaseOrderLine {

    /**
     */
    private String plantExternalId;

    /**
     */
    private Float totalCost;

    /**
     */
    private String plantName;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar startDate;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar endDate;

    /**
     */
    @ManyToOne
    private PurchaseOrder request;
}
