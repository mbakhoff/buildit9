// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.rest;

import esi.buildit9.rest.PurchaseOrderListResource;
import esi.buildit9.rest.PurchaseOrderResource;
import java.util.Set;

privileged aspect PurchaseOrderListResource_Roo_JavaBean {
    
    public Set<PurchaseOrderResource> PurchaseOrderListResource.getPurchaseOrders() {
        return this.purchaseOrders;
    }
    
    public void PurchaseOrderListResource.setPurchaseOrders(Set<PurchaseOrderResource> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }
    
}
