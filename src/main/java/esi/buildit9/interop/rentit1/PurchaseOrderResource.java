package esi.buildit9.interop.rentit1;

import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Date;

@RooJavaBean
@XmlRootElement(name="purchaseorder")
public class PurchaseOrderResource {

    private PlantResource plantId;

    private Short status;

    private Date startdate;

    private Date enddate;

    private BigDecimal price;

    private String commentt;

    private String customerEmail;

    private String PoRejectionlink;

    private String PoExtensionRejectionlink;

    public String getPoRejectionlink() {
        return PoRejectionlink;
    }

    public void setPoRejectionlink(String poRejectionlink) {
        PoRejectionlink = poRejectionlink;
    }

    public String getPoExtensionRejectionlink() {
        return PoExtensionRejectionlink;
    }

    public void setPoExtensionRejectionlink(String poExtensionRejectionlink) {
        PoExtensionRejectionlink = poExtensionRejectionlink;
    }


    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCommentt() {
        return commentt;
    }

    public void setCommentt(String commentt) {
        this.commentt = commentt;
    }

    public PlantResource getPlantId() {
        return plantId;
    }
    public void setPlantId(PlantResource plantId) {
        this.plantId = plantId;
    }
    public Short getStatus() {
        return status;
    }
    public void setStatus(Short status) {
        this.status = status;
    }
    public Date getStartdate() {
        return startdate;
    }
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }
    public Date getEnddate() {
        return enddate;
    }
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
