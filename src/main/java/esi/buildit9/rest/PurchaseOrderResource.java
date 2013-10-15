package esi.buildit9.rest;

import esi.buildit9.domain.Site;
import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@RooJavaBean
@XmlRootElement(name="purchaseorder")
public class PurchaseOrderResource {

    private String buildit;
    private String rentit;
	private String siteAddress;
    private List<PurchaseOrderLineResource> purchaseOrderLines;

    public Site getOrCreateSite(String siteAddress) {
        //TODO implement findSiteAddress
        return null;  //To change body of created methods use File | Settings | File Templates.
    }
}
