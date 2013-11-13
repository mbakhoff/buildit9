package esi.buildit9.domain;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findPurchaseOrdersBySiteEngineerNameEquals" })
public class PurchaseOrder {

    /**
     */
    private float totalPrice;

    /**
     */
    @ManyToOne
    private Site site;

    /**
     */
    @ManyToOne
    private RentIt rentit;

    /**
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    /**
     */
    private String siteEngineerName;

    /**
     */
    private String worksEngineerName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseOrder")
    private Set<PurchaseOrderLine> lines;
}
