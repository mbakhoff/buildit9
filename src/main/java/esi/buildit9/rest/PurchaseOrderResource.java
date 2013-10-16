package esi.buildit9.rest;

import esi.buildit9.domain.OrderStatus;
import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@RooJavaBean
@XmlRootElement(name="purchaseorder")
public class PurchaseOrderResource {

	private Long id;
    private String buildit;
    private String rentit;
	private String siteAddress;
    private OrderStatus orderStatus;
    private float totalPrice;
    private String siteEngineerName;
    private String worksEngineerName;

    private List<PurchaseOrderLineResource> purchaseOrderLines;
}
