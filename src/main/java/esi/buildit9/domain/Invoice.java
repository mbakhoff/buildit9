package esi.buildit9.domain;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findInvoicesByPurchaseOrder" })
public class Invoice {

    /**
     */
    @ManyToOne
    private PurchaseOrder purchaseOrder;

    /**
     */
    @ManyToOne
    private RentIt rentit;

    private String senderEmail;

    private InvoiceStatus status;

    private Long idAtRentit;

    public boolean isMarkedForPayment() {
        return status == InvoiceStatus.APPROVED || status == InvoiceStatus.COMPLETED;
    }
}
