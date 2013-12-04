package esi.buildit9.dto;

import esi.buildit9.domain.OrderStatus;
import esi.buildit9.soap.client.PlantResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RooJavaBean
public class CreatePurchaseOrderDTO {

    private String nameLike;
    private String siteAddress;
    private String worksEngineerName;
    private String selectedPlantId;
    private String selectedPlantName;
    private OrderStatus orderStatus;
    private List<PlantLineDTO> searchLines;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar endDate;

    public CreatePurchaseOrderDTO() {
        orderStatus = OrderStatus.CREATED;
        searchLines = new ArrayList<PlantLineDTO>();
    }

    public void setLinesFromPlants(List<PlantResource> plantList) {
        searchLines = new ArrayList<PlantLineDTO>();
        for (PlantResource plant : plantList) {
            PlantLineDTO pl = new PlantLineDTO();
            pl.setChecked(false);
            pl.setId(plant.getId());
            pl.setName(plant.getName());
            pl.setDescription(plant.getDescription());
            searchLines.add(pl);
        }
    }

}
