package esi.buildit9.interop.rentit30;

import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@RooJavaBean
@XmlRootElement(name = "purchaseorders")
public class PurchaseOrderResourceList {
private List<PurchaseOrderResource> orders;
	
	public PurchaseOrderResourceList() {
		orders = new LinkedList<PurchaseOrderResource>();
	}
	
	public PurchaseOrderResourceList(List<PurchaseOrderResource> list) { 
		orders = list;
	}

	@XmlElement(name = "order")
	public List<PurchaseOrderResource> getOrders() {
        return this.orders;
    }

	public void setOrders(List<PurchaseOrderResource> orders) {
        this.orders = orders;
    }
}
