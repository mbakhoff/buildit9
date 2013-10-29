
package esi.buildit9.soap.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for plantResourceList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="plantResourceList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://web.soap.rentit9.esi/}plant" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "plantResourceList", propOrder = {
    "plant"
})
public class PlantResourceList {

    @XmlElement(namespace = "http://web.soap.rentit9.esi/")
    protected List<PlantResource> plant;

    /**
     * Gets the value of the plant property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the plant property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlant().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlantResource }
     * 
     * 
     */
    public List<PlantResource> getPlant() {
        if (plant == null) {
            plant = new ArrayList<PlantResource>();
        }
        return this.plant;
    }

}
