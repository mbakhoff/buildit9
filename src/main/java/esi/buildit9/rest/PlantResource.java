package esi.buildit9.rest;

import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlRootElement;

@RooJavaBean
@XmlRootElement(name="plant")
public class PlantResource {

    private Long id;

	private String name;

	private String description;

	private Float price;

}
