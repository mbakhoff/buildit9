package esi.buildit9.rest;

import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Calendar;

@RooJavaBean
@XmlRootElement(name = "purchaseorder")
@XmlAccessorType(XmlAccessType.FIELD)
public class PurchaseOrderResource {

    private String rentitOrderId;
    private String builditOrderId;
    private String plantId;
    private String buildit;
    private String rentit;
    private String siteAddress;
    private String status;
    private Float total;
    private Calendar startDate;
    private Calendar endDate;

    private String plantName;
    private String siteEngineerName;
    private String worksEngineerName;

}
