// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.dto;

import esi.buildit9.domain.RentIt;
import esi.buildit9.dto.CreatePurchaseOrderDTO;
import esi.buildit9.dto.PlantLineDTO;
import java.util.Calendar;
import java.util.List;

privileged aspect CreatePurchaseOrderDTO_Roo_JavaBean {
    
    public String CreatePurchaseOrderDTO.getNameLike() {
        return this.nameLike;
    }
    
    public void CreatePurchaseOrderDTO.setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }
    
    public String CreatePurchaseOrderDTO.getSiteAddress() {
        return this.siteAddress;
    }
    
    public void CreatePurchaseOrderDTO.setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }
    
    public String CreatePurchaseOrderDTO.getWorksEngineerName() {
        return this.worksEngineerName;
    }
    
    public void CreatePurchaseOrderDTO.setWorksEngineerName(String worksEngineerName) {
        this.worksEngineerName = worksEngineerName;
    }
    
    public Integer CreatePurchaseOrderDTO.getSelectedItem() {
        return this.selectedItem;
    }
    
    public void CreatePurchaseOrderDTO.setSelectedItem(Integer selectedItem) {
        this.selectedItem = selectedItem;
    }
    
    public RentIt CreatePurchaseOrderDTO.getRentIt() {
        return this.rentIt;
    }
    
    public void CreatePurchaseOrderDTO.setRentIt(RentIt rentIt) {
        this.rentIt = rentIt;
    }
    
    public List<PlantLineDTO> CreatePurchaseOrderDTO.getSearchLines() {
        return this.searchLines;
    }
    
    public void CreatePurchaseOrderDTO.setSearchLines(List<PlantLineDTO> searchLines) {
        this.searchLines = searchLines;
    }
    
    public Calendar CreatePurchaseOrderDTO.getStartDate() {
        return this.startDate;
    }
    
    public void CreatePurchaseOrderDTO.setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }
    
    public Calendar CreatePurchaseOrderDTO.getEndDate() {
        return this.endDate;
    }
    
    public void CreatePurchaseOrderDTO.setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }
    
}
