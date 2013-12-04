package esi.buildit9.rest;

import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@RooJavaBean
@XmlRootElement(name="plant")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlantResource {

    private Long id;
	private String name;
	private String description;
	private Float price;

}
