
package esi.buildit9.soap.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the esi.rentit9.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Plant_QNAME = new QName("http://web.soap.rentit9.esi/", "plant");
    private final static QName _GetAllPlants_QNAME = new QName("http://web.soap.rentit9.esi/", "getAllPlants");
    private final static QName _GetAllPlantsResponse_QNAME = new QName("http://web.soap.rentit9.esi/", "getAllPlantsResponse");
    private final static QName _Plants_QNAME = new QName("http://web.soap.rentit9.esi/", "plants");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: esi.rentit9.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PlantResource }
     * 
     */
    public PlantResource createPlantResource() {
        return new PlantResource();
    }

    /**
     * Create an instance of {@link PlantListResource }
     * 
     */
    public PlantListResource createPlantListResource() {
        return new PlantListResource();
    }

    /**
     * Create an instance of {@link GetAllPlantsResponse }
     * 
     */
    public GetAllPlantsResponse createGetAllPlantsResponse() {
        return new GetAllPlantsResponse();
    }

    /**
     * Create an instance of {@link GetAllPlants }
     * 
     */
    public GetAllPlants createGetAllPlants() {
        return new GetAllPlants();
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link PlantResource }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "plant")
    public JAXBElement<PlantResource> createPlant(PlantResource value) {
        return new JAXBElement<PlantResource>(_Plant_QNAME, PlantResource.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link GetAllPlants }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "getAllPlants")
    public JAXBElement<GetAllPlants> createGetAllPlants(GetAllPlants value) {
        return new JAXBElement<GetAllPlants>(_GetAllPlants_QNAME, GetAllPlants.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link GetAllPlantsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "getAllPlantsResponse")
    public JAXBElement<GetAllPlantsResponse> createGetAllPlantsResponse(GetAllPlantsResponse value) {
        return new JAXBElement<GetAllPlantsResponse>(_GetAllPlantsResponse_QNAME, GetAllPlantsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link PlantListResource }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "plants")
    public JAXBElement<PlantListResource> createPlants(PlantListResource value) {
        return new JAXBElement<PlantListResource>(_Plants_QNAME, PlantListResource.class, null, value);
    }

}
