// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.domain;

import esi.buildit9.domain.PlantHireRequest;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.Site;
import java.util.Calendar;

privileged aspect PurchaseOrder_Roo_JavaBean {
    
    public Site PurchaseOrder.getSite() {
        return this.site;
    }
    
    public void PurchaseOrder.setSite(Site site) {
        this.site = site;
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
    
    public PlantHireRequest PurchaseOrder.getHireRequest() {
        return this.hireRequest;
    }
    
    public void PurchaseOrder.setHireRequest(PlantHireRequest hireRequest) {
        this.hireRequest = hireRequest;
    }
    
    public String PurchaseOrder.getWorksEngineerName() {
        return this.worksEngineerName;
    }
    
    public void PurchaseOrder.setWorksEngineerName(String worksEngineerName) {
        this.worksEngineerName = worksEngineerName;
    }
    
}
