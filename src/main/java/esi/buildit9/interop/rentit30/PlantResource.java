package esi.buildit9.interop.rentit30;

import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@RooJavaBean
@XmlRootElement(name = "plant")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlantResource{
    private String title;
    private Integer plantId;
    private Double price;
    private String description;
}
