package esi.buildit9.interop.rentit30;

import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

@XmlRootElement(name = "purchaseorder")
@XmlAccessorType(XmlAccessType.FIELD)
@RooJavaBean
public class PurchaseOrderResource{
	
	private Long hireRequestId;
	private PlantResource plant;
	private Long poId;
	private Double price;
	private Integer siteId;
	private POStatus status;
	private Boolean paid;
	
	private Date startDate;
	
	private Date endDate;
    private String email;
    private String getlink;
    private String credentials;

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getStart() {
        return this.startDate;
    }

	public void setStart(Date start) {
        this.startDate = start;
    }

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getEnd() {
        return this.endDate;
    }

	public void setEnd(Date end) {
        this.endDate = end;
    }
}