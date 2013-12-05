package esi.buildit9.interop.rentit1;

import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@RooJavaBean
@XmlRootElement(name = "POS")
public class PurchaseOrderResourceList{

    private List<PurchaseOrderResource> po = new ArrayList<PurchaseOrderResource>();

    public PurchaseOrderResourceList() {

    }

    public PurchaseOrderResourceList(List<PurchaseOrderResource> PurchaseOrderResources){
        this.po = PurchaseOrderResources;
    }

    public List<PurchaseOrderResource> getPo() {
        return po;
    }

    public void setPo(List<PurchaseOrderResource> po) {
        this.po = po;
    }
}

