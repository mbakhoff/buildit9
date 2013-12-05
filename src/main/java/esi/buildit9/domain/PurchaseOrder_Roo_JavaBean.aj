// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.domain;

import esi.buildit9.domain.OrderStatus;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RentIt;
import esi.buildit9.domain.Site;
import java.util.Calendar;

privileged aspect PurchaseOrder_Roo_JavaBean {
    
    public Site PurchaseOrder.getSite() {
        return this.site;
    }
    
    public void PurchaseOrder.setSite(Site site) {
        this.site = site;
    }
    
    public RentIt PurchaseOrder.getRentit() {
        return this.rentit;
    }
    
    public void PurchaseOrder.setRentit(RentIt rentit) {
        this.rentit = rentit;
    }
    
    public String PurchaseOrder.getPlantExternalId() {
        return this.plantExternalId;
    }
    
    public void PurchaseOrder.setPlantExternalId(String plantExternalId) {
        this.plantExternalId = plantExternalId;
    }
    
    public String PurchaseOrder.getIdAtRentit() {
        return this.idAtRentit;
    }
    
    public void PurchaseOrder.setIdAtRentit(String idAtRentit) {
        this.idAtRentit = idAtRentit;
    }
    
    public String PurchaseOrder.getPlantName() {
        return this.plantName;
    }
    
    public void PurchaseOrder.setPlantName(String plantName) {
        this.plantName = plantName;
    }
    
    public Float PurchaseOrder.getTotalPrice() {
        return this.totalPrice;
    }
    
    public void PurchaseOrder.setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public OrderStatus PurchaseOrder.getOrderStatus() {
        return this.orderStatus;
    }
    
    public void PurchaseOrder.setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    public Calendar PurchaseOrder.getStartDate() {
        return this.startDate;
    }
    
    public void PurchaseOrder.setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }
    
    public Calendar PurchaseOrder.getEndDate() {
        return this.endDate;
    }
    
    public void PurchaseOrder.setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }
    
    public String PurchaseOrder.getSiteEngineerName() {
        return this.siteEngineerName;
    }
    
    public void PurchaseOrder.setSiteEngineerName(String siteEngineerName) {
        this.siteEngineerName = siteEngineerName;
    }
    
    public String PurchaseOrder.getWorksEngineerName() {
        return this.worksEngineerName;
    }
    
    public void PurchaseOrder.setWorksEngineerName(String worksEngineerName) {
        this.worksEngineerName = worksEngineerName;
    }
    
}
