// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.rest;

import esi.buildit9.domain.OrderStatus;
import esi.buildit9.rest.PurchaseOrderLineListResource;
import esi.buildit9.rest.PurchaseOrderResource;

privileged aspect PurchaseOrderResource_Roo_JavaBean {
    
    public Long PurchaseOrderResource.getId() {
        return this.id;
    }
    
    public void PurchaseOrderResource.setId(Long id) {
        this.id = id;
    }
    
    public String PurchaseOrderResource.getBuildit() {
        return this.buildit;
    }
    
    public void PurchaseOrderResource.setBuildit(String buildit) {
        this.buildit = buildit;
    }
    
    public String PurchaseOrderResource.getRentit() {
        return this.rentit;
    }
    
    public void PurchaseOrderResource.setRentit(String rentit) {
        this.rentit = rentit;
    }
    
    public String PurchaseOrderResource.getSiteAddress() {
        return this.siteAddress;
    }
    
    public void PurchaseOrderResource.setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }
    
    public OrderStatus PurchaseOrderResource.getOrderStatus() {
        return this.orderStatus;
    }
    
    public void PurchaseOrderResource.setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    public float PurchaseOrderResource.getTotalPrice() {
        return this.totalPrice;
    }
    
    public void PurchaseOrderResource.setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public String PurchaseOrderResource.getSiteEngineerName() {
        return this.siteEngineerName;
    }
    
    public void PurchaseOrderResource.setSiteEngineerName(String siteEngineerName) {
        this.siteEngineerName = siteEngineerName;
    }
    
    public String PurchaseOrderResource.getWorksEngineerName() {
        return this.worksEngineerName;
    }
    
    public void PurchaseOrderResource.setWorksEngineerName(String worksEngineerName) {
        this.worksEngineerName = worksEngineerName;
    }
    
    public PurchaseOrderLineListResource PurchaseOrderResource.getOrderLines() {
        return this.orderLines;
    }
    
    public void PurchaseOrderResource.setOrderLines(PurchaseOrderLineListResource orderLines) {
        this.orderLines = orderLines;
    }
    
}
