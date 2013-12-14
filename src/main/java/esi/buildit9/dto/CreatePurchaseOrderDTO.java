package esi.buildit9.dto;

import esi.buildit9.domain.RentIt;
import esi.buildit9.rest.PlantResource;
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
    private Integer selectedItem;
    private RentIt rentIt;

    private List<PlantLineDTO> searchLines;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar endDate;

    public CreatePurchaseOrderDTO() {
        searchLines = new ArrayList<PlantLineDTO>();
    }

    public void setLinesFromPlants(List<PlantResource> plantList) {
        searchLines = new ArrayList<PlantLineDTO>();
        if (plantList.isEmpty()) return;
        for (PlantResource plant : plantList) {
            searchLines.add(createLine(plant));
        }
    }

    private static PlantLineDTO createLine(PlantResource plant) {
        PlantLineDTO item = new PlantLineDTO();
        item.setId(plant.getId());
        item.setName(plant.getName());
        item.setDescription(plant.getDescription());
        item.setPrice(plant.getPrice());
        return item;
    }


}
