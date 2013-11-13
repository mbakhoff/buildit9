package esi.buildit9.dto;

import esi.buildit9.soap.client.PlantResource;
import org.springframework.roo.addon.javabean.RooJavaBean;

import java.util.ArrayList;
import java.util.List;

@RooJavaBean
public class AddLinesDTO {
   private List<PlantLineDTO> lines;

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
        lines=l;
    }
}

