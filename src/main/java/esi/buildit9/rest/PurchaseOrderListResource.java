package esi.buildit9.rest;

import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

@RooJavaBean
@XmlRootElement(name = "purchaseorders")
@XmlAccessorType(XmlAccessType.FIELD)
public class PurchaseOrderListResource {

    @XmlElement(name="purchaseorder")
    public Set<PurchaseOrderResource> purchaseOrders;

    public PurchaseOrderListResource() {
        purchaseOrders = new HashSet<PurchaseOrderResource>();
    }
}
