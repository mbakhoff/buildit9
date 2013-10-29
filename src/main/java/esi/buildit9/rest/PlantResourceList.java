package esi.buildit9.rest;

import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@RooJavaBean
@XmlRootElement(name="plants")
public class PlantResourceList {

	private List<PlantResource> plant = new ArrayList<PlantResource>();

}
