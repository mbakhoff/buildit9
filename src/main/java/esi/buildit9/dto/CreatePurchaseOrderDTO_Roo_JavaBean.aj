// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.dto;

import esi.buildit9.domain.OrderStatus;
import esi.buildit9.domain.PurchaseOrderLine;
import esi.buildit9.dto.CreatePurchaseOrderDTO;
import esi.buildit9.dto.PlantLineDTO;
import esi.buildit9.dto.PlantQueryDTO;
import java.util.List;

privileged aspect CreatePurchaseOrderDTO_Roo_JavaBean {
    
    public PlantQueryDTO CreatePurchaseOrderDTO.getPlantsQuery() {
        return this.plantsQuery;
    }
    
    public void CreatePurchaseOrderDTO.setPlantsQuery(PlantQueryDTO plantsQuery) {
        this.plantsQuery = plantsQuery;
    }
    
    public OrderStatus CreatePurchaseOrderDTO.getOrderStatus() {
        return this.orderStatus;
    }
    
    public void CreatePurchaseOrderDTO.setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    public List<PurchaseOrderLine> CreatePurchaseOrderDTO.getAddedLines() {
        return this.addedLines;
    }
    
    public void CreatePurchaseOrderDTO.setAddedLines(List<PurchaseOrderLine> addedLines) {
        this.addedLines = addedLines;
    }
    
    public List<PlantLineDTO> CreatePurchaseOrderDTO.getSearchLines() {
        return this.searchLines;
    }
    
    public void CreatePurchaseOrderDTO.setSearchLines(List<PlantLineDTO> searchLines) {
        this.searchLines = searchLines;
    }
    
}
