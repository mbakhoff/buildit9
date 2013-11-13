package esi.buildit9.dto;

import esi.buildit9.domain.OrderStatus;
import esi.buildit9.domain.PurchaseOrderLine;
import esi.buildit9.soap.client.PlantResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.AutoPopulatingList;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RooJavaBean
public class CreatePurchaseOrderDTO {
    //private AddLinesDTO addLines;
    //private PlantQueryDTO plantsQuery;
    private String nameLike;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar endDate;

    public CreatePurchaseOrderDTO() {
        orderStatus = OrderStatus.CREATED;
    }

    private OrderStatus orderStatus;

    private List<PurchaseOrderLine> addedLines = new ArrayList<PurchaseOrderLine>();
    private List<PlantLineDTO> searchLines =new ArrayList<PlantLineDTO>();

    public void createFromPlants(List<PlantResource> plantList) {
        List<PlantLineDTO> l=new ArrayList<PlantLineDTO>();
        for (PlantResource plant:plantList){
            PlantLineDTO pl=new PlantLineDTO();
            pl.setChecked(false);
            pl.setId(plant.getId());
            pl.setName(plant.getName());
            pl.setDescription(plant.getDescription());
            l.add(pl);
        }
        searchLines=l;
    }
}
