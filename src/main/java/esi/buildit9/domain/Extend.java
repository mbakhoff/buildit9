package esi.buildit9.domain;
import java.util.Calendar;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Extend {
	
	@Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Calendar endDate;
	
	@ManyToOne
    private PurchaseOrder purchaseOrder;
	
    @Enumerated(EnumType.STRING)
    private ExtendStatus extendStatus;
}
