package esi.buildit9.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

//@RooJavaBean
@XmlRootElement(name = "orderlines")
public class PurchaseOrderLineListResource {
    @XmlElement(name = "line")
    public List<PurchaseOrderLineResource> orderLines;

    public PurchaseOrderLineListResource() {
        this.orderLines = new ArrayList<PurchaseOrderLineResource>();
    }
}
