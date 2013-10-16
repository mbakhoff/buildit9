package esi.buildit9.rest;

import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

//@RooJavaBean
@XmlRootElement(name = "orderlines")
public class PurchaseOrderLineListResource {
    @XmlElement(name = "orderline")
    public List<PurchaseOrderLineResource> orderLines;

    public PurchaseOrderLineListResource() {
        this.orderLines = new ArrayList<PurchaseOrderLineResource>();
    }
}
