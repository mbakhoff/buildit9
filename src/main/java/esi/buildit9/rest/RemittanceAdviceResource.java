package esi.buildit9.rest;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;

import esi.buildit9.domain.Invoice;
import esi.buildit9.rest.util.ExtendedResourceSupport;

@RooJavaBean
@XmlRootElement(name = "remittanceadvice")
public class RemittanceAdviceResource extends ExtendedResourceSupport{
	
	private Invoice invoice;
	private Calendar payDay;
	
}
