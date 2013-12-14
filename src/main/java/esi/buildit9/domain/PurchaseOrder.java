package esi.buildit9.domain;
import org.joda.time.DateMidnight;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;

import java.util.Calendar;
import java.util.List;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findPurchaseOrdersByWorksEngineerNameEquals", "findPurchaseOrdersBySiteEngineerNameEquals" })
public class PurchaseOrder {
	
    private static final String QUERY_ORDERS_FOR_WORKERS =
            "FROM PurchaseOrder AS order WHERE " +
                    "order.orderStatus != :postatus";

    @ManyToOne
    private Site site;

    @ManyToOne
    private RentIt rentit;

    private String plantExternalId;

    private String idAtRentit;

    private String plantName;

    private Float totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Calendar startDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Calendar endDate;

    private String siteEngineerName;

    private String worksEngineerName;
    
    public static List<PurchaseOrder> getPOsForWorkers() {
        TypedQuery<PurchaseOrder> query = entityManager().createQuery(
                QUERY_ORDERS_FOR_WORKERS,
                PurchaseOrder.class);
        query.setParameter("postatus", OrderStatus.WAITING_APPROVAL);
        return query.getResultList();
    }

}
