package esi.buildit9.rest;

import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Calendar;

@RooJavaBean
@XmlRootElement(name = "purchaseorderline")
public class PurchaseOrderLineResource {

    private String plantId;
    private String plantName;
    private Calendar startDate;
    private Calendar endDate;
    private float totalCost;
}
