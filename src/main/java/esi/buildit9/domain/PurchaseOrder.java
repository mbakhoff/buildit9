package esi.buildit9.domain;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
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

    @OneToMany
    private List<PurchaseOrderLine> lines;

}
