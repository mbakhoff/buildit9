package esi.buildit9.service;

import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlRootElement;

@RooJavaBean
@XmlRootElement
public class InvoiceResource {

    private Long id;
    private Long po;
    private Float total;

}
