package esi.buildit9.interop.rentit1;

import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@RooJavaBean
@XmlRootElement(name="plants")
public class PlantResourceList {

    private List<PlantResource> plantResources = new ArrayList<PlantResource>();

    public PlantResourceList() {

    }

    public PlantResourceList(List<PlantResource> plantResources){
        this.plantResources = plantResources;
    }

    public List<PlantResource> getPlantResources() {
        return plantResources;
    }

    public void setPlantResources(List<PlantResource> plantResources) {
        this.plantResources = plantResources;
    }
}
