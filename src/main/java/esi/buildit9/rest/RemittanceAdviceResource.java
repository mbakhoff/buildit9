package esi.buildit9.rest;


import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Calendar;

@RooJavaBean
@XmlRootElement(name = "remittanceadvice")
@XmlAccessorType(XmlAccessType.FIELD)
public class RemittanceAdviceResource {
	
	private Long rentitInvoiceId;
	private Calendar paymentDate;
	
}
