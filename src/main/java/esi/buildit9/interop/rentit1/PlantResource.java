package esi.buildit9.interop.rentit1;


import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@RooJavaBean
@XmlRootElement(name="plant")
public class PlantResource {

    private Long id;
    private String name;
    private BigDecimal pricePrDay;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getPricePrDay() {
        return pricePrDay;
    }
    public void setPricePrDay(BigDecimal pricePrDay) {
        this.pricePrDay = pricePrDay;
    }
}
