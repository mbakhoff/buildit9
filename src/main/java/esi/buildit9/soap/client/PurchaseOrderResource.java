
package esi.buildit9.soap.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for purchaseOrderResource complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="purchaseOrderResource">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="buildit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="internalId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="purchaseOrderLines" type="{http://web.soap.rentit9.esi/}purchaseOrderLineResourceList" minOccurs="0"/>
 *         &lt;element name="senderSideId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="siteAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://web.soap.rentit9.esi/}orderStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "purchaseOrderResource", propOrder = {
    "buildit",
    "internalId",
    "purchaseOrderLines",
    "senderSideId",
    "siteAddress",
    "status"
})
public class PurchaseOrderResource {

    protected String buildit;
    protected Long internalId;
    protected PurchaseOrderLineResourceList purchaseOrderLines;
    protected String senderSideId;
    protected String siteAddress;
    protected OrderStatus status;

    /**
     * Gets the value of the buildit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuildit() {
        return buildit;
    }

    /**
     * Sets the value of the buildit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuildit(String value) {
        this.buildit = value;
    }

    /**
     * Gets the value of the internalId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getInternalId() {
        return internalId;
    }

    /**
     * Sets the value of the internalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setInternalId(Long value) {
        this.internalId = value;
    }

    /**
     * Gets the value of the purchaseOrderLines property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseOrderLineResourceList }
     *     
     */
    public PurchaseOrderLineResourceList getPurchaseOrderLines() {
        return purchaseOrderLines;
    }

    /**
     * Sets the value of the purchaseOrderLines property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseOrderLineResourceList }
     *     
     */
    public void setPurchaseOrderLines(PurchaseOrderLineResourceList value) {
        this.purchaseOrderLines = value;
    }

    /**
     * Gets the value of the senderSideId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderSideId() {
        return senderSideId;
    }

    /**
     * Sets the value of the senderSideId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderSideId(String value) {
        this.senderSideId = value;
    }

    /**
     * Gets the value of the siteAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteAddress() {
        return siteAddress;
    }

    /**
     * Sets the value of the siteAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteAddress(String value) {
        this.siteAddress = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link OrderStatus }
     *     
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderStatus }
     *     
     */
    public void setStatus(OrderStatus value) {
        this.status = value;
    }

}
