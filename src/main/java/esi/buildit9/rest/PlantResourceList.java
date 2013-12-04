package esi.buildit9.rest;

import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@RooJavaBean
@XmlRootElement(name="plants")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlantResourceList {

    @XmlElement(name = "plant")
	public List<PlantResource> plants;

    @SuppressWarnings("UnusedDeclaration")
    public PlantResourceList() {
        plants = new ArrayList<PlantResource>();
    }

}
