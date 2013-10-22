package esi.buildit9.rest;

import esi.buildit9.domain.OrderStatus;
import esi.buildit9.rest.util.ExtendedResourceSupport;
import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlRootElement;

@RooJavaBean
@XmlRootElement(name = "purchaseorder")
public class PurchaseOrderResource extends ExtendedResourceSupport {

	private Long internalId;
    private String buildit;
    private String rentit;
    private String siteAddress;
    private OrderStatus orderStatus;
    private float totalPrice;
    private String siteEngineerName;
    private String worksEngineerName;

    private PurchaseOrderLineListResource orderLines;
}
