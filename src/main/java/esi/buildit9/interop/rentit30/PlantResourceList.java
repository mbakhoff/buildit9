package esi.buildit9.interop.rentit30;

import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@RooJavaBean
@XmlRootElement(name = "plants")
public class PlantResourceList {

    private List<PlantResource> plants;

    public PlantResourceList() {
        plants = new LinkedList<PlantResource>();
    }

    public PlantResourceList(List<PlantResource> list) {
        plants = list;
    }

    @XmlElement(name = "plant")
    public List<PlantResource> getPlants() {
        return this.plants;
    }

    public void setPlants(List<PlantResource> plants) {
        this.plants = plants;
    }
}

