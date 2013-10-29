
package esi.buildit9.soap.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the esi.buildit9.soap.client package. 
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

    private final static QName _GetPlantsBetweenResponse_QNAME = new QName("http://web.soap.rentit9.esi/", "getPlantsBetweenResponse");
    private final static QName _Getplantsrequest_QNAME = new QName("http://web.soap.rentit9.esi/", "getplantsrequest");
    private final static QName _UpdatePurchaseOrderResponse_QNAME = new QName("http://web.soap.rentit9.esi/", "updatePurchaseOrderResponse");
    private final static QName _GetAllPlants_QNAME = new QName("http://web.soap.rentit9.esi/", "getAllPlants");
    private final static QName _CreatePurchaseOrderResponse_QNAME = new QName("http://web.soap.rentit9.esi/", "createPurchaseOrderResponse");
    private final static QName _GetPlantsBetween_QNAME = new QName("http://web.soap.rentit9.esi/", "getPlantsBetween");
    private final static QName _UpdatePurchaseOrder_QNAME = new QName("http://web.soap.rentit9.esi/", "updatePurchaseOrder");
    private final static QName _CreatePurchaseOrder_QNAME = new QName("http://web.soap.rentit9.esi/", "createPurchaseOrder");
    private final static QName _Getplantsbetweenrequest_QNAME = new QName("http://web.soap.rentit9.esi/", "getplantsbetweenrequest");
    private final static QName _Plant_QNAME = new QName("http://web.soap.rentit9.esi/", "plant");
    private final static QName _Purchaseorderlines_QNAME = new QName("http://web.soap.rentit9.esi/", "purchaseorderlines");
    private final static QName _Purchaseorder_QNAME = new QName("http://web.soap.rentit9.esi/", "purchaseorder");
    private final static QName _Purchaseorderline_QNAME = new QName("http://web.soap.rentit9.esi/", "purchaseorderline");
    private final static QName _GetAllPlantsResponse_QNAME = new QName("http://web.soap.rentit9.esi/", "getAllPlantsResponse");
    private final static QName _Plants_QNAME = new QName("http://web.soap.rentit9.esi/", "plants");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: esi.buildit9.soap.client
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
     * Create an instance of {@link GetPlantsBetweenResource }
     * 
     */
    public GetPlantsBetweenResource createGetPlantsBetweenResource() {
        return new GetPlantsBetweenResource();
    }

    /**
     * Create an instance of {@link PlantResourceList }
     * 
     */
    public PlantResourceList createPlantResourceList() {
        return new PlantResourceList();
    }

    /**
     * Create an instance of {@link GetAllPlantsResponse }
     * 
     */
    public GetAllPlantsResponse createGetAllPlantsResponse() {
        return new GetAllPlantsResponse();
    }

    /**
     * Create an instance of {@link PurchaseOrderResource }
     * 
     */
    public PurchaseOrderResource createPurchaseOrderResource() {
        return new PurchaseOrderResource();
    }

    /**
     * Create an instance of {@link PurchaseOrderLineResource }
     * 
     */
    public PurchaseOrderLineResource createPurchaseOrderLineResource() {
        return new PurchaseOrderLineResource();
    }

    /**
     * Create an instance of {@link PurchaseOrderLineResourceList }
     * 
     */
    public PurchaseOrderLineResourceList createPurchaseOrderLineResourceList() {
        return new PurchaseOrderLineResourceList();
    }

    /**
     * Create an instance of {@link UpdatePurchaseOrderResponse }
     * 
     */
    public UpdatePurchaseOrderResponse createUpdatePurchaseOrderResponse() {
        return new UpdatePurchaseOrderResponse();
    }

    /**
     * Create an instance of {@link GetPlantsResource }
     * 
     */
    public GetPlantsResource createGetPlantsResource() {
        return new GetPlantsResource();
    }

    /**
     * Create an instance of {@link GetPlantsBetweenResponse }
     * 
     */
    public GetPlantsBetweenResponse createGetPlantsBetweenResponse() {
        return new GetPlantsBetweenResponse();
    }

    /**
     * Create an instance of {@link CreatePurchaseOrder }
     * 
     */
    public CreatePurchaseOrder createCreatePurchaseOrder() {
        return new CreatePurchaseOrder();
    }

    /**
     * Create an instance of {@link UpdatePurchaseOrder }
     * 
     */
    public UpdatePurchaseOrder createUpdatePurchaseOrder() {
        return new UpdatePurchaseOrder();
    }

    /**
     * Create an instance of {@link GetPlantsBetween }
     * 
     */
    public GetPlantsBetween createGetPlantsBetween() {
        return new GetPlantsBetween();
    }

    /**
     * Create an instance of {@link CreatePurchaseOrderResponse }
     * 
     */
    public CreatePurchaseOrderResponse createCreatePurchaseOrderResponse() {
        return new CreatePurchaseOrderResponse();
    }

    /**
     * Create an instance of {@link GetAllPlants }
     * 
     */
    public GetAllPlants createGetAllPlants() {
        return new GetAllPlants();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlantsBetweenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "getPlantsBetweenResponse")
    public JAXBElement<GetPlantsBetweenResponse> createGetPlantsBetweenResponse(GetPlantsBetweenResponse value) {
        return new JAXBElement<GetPlantsBetweenResponse>(_GetPlantsBetweenResponse_QNAME, GetPlantsBetweenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlantsResource }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "getplantsrequest")
    public JAXBElement<GetPlantsResource> createGetplantsrequest(GetPlantsResource value) {
        return new JAXBElement<GetPlantsResource>(_Getplantsrequest_QNAME, GetPlantsResource.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePurchaseOrderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "updatePurchaseOrderResponse")
    public JAXBElement<UpdatePurchaseOrderResponse> createUpdatePurchaseOrderResponse(UpdatePurchaseOrderResponse value) {
        return new JAXBElement<UpdatePurchaseOrderResponse>(_UpdatePurchaseOrderResponse_QNAME, UpdatePurchaseOrderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllPlants }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "getAllPlants")
    public JAXBElement<GetAllPlants> createGetAllPlants(GetAllPlants value) {
        return new JAXBElement<GetAllPlants>(_GetAllPlants_QNAME, GetAllPlants.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePurchaseOrderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "createPurchaseOrderResponse")
    public JAXBElement<CreatePurchaseOrderResponse> createCreatePurchaseOrderResponse(CreatePurchaseOrderResponse value) {
        return new JAXBElement<CreatePurchaseOrderResponse>(_CreatePurchaseOrderResponse_QNAME, CreatePurchaseOrderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlantsBetween }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "getPlantsBetween")
    public JAXBElement<GetPlantsBetween> createGetPlantsBetween(GetPlantsBetween value) {
        return new JAXBElement<GetPlantsBetween>(_GetPlantsBetween_QNAME, GetPlantsBetween.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePurchaseOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "updatePurchaseOrder")
    public JAXBElement<UpdatePurchaseOrder> createUpdatePurchaseOrder(UpdatePurchaseOrder value) {
        return new JAXBElement<UpdatePurchaseOrder>(_UpdatePurchaseOrder_QNAME, UpdatePurchaseOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePurchaseOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "createPurchaseOrder")
    public JAXBElement<CreatePurchaseOrder> createCreatePurchaseOrder(CreatePurchaseOrder value) {
        return new JAXBElement<CreatePurchaseOrder>(_CreatePurchaseOrder_QNAME, CreatePurchaseOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlantsBetweenResource }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "getplantsbetweenrequest")
    public JAXBElement<GetPlantsBetweenResource> createGetplantsbetweenrequest(GetPlantsBetweenResource value) {
        return new JAXBElement<GetPlantsBetweenResource>(_Getplantsbetweenrequest_QNAME, GetPlantsBetweenResource.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlantResource }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "plant")
    public JAXBElement<PlantResource> createPlant(PlantResource value) {
        return new JAXBElement<PlantResource>(_Plant_QNAME, PlantResource.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PurchaseOrderLineResourceList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "purchaseorderlines")
    public JAXBElement<PurchaseOrderLineResourceList> createPurchaseorderlines(PurchaseOrderLineResourceList value) {
        return new JAXBElement<PurchaseOrderLineResourceList>(_Purchaseorderlines_QNAME, PurchaseOrderLineResourceList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PurchaseOrderResource }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "purchaseorder")
    public JAXBElement<PurchaseOrderResource> createPurchaseorder(PurchaseOrderResource value) {
        return new JAXBElement<PurchaseOrderResource>(_Purchaseorder_QNAME, PurchaseOrderResource.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PurchaseOrderLineResource }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "purchaseorderline")
    public JAXBElement<PurchaseOrderLineResource> createPurchaseorderline(PurchaseOrderLineResource value) {
        return new JAXBElement<PurchaseOrderLineResource>(_Purchaseorderline_QNAME, PurchaseOrderLineResource.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllPlantsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "getAllPlantsResponse")
    public JAXBElement<GetAllPlantsResponse> createGetAllPlantsResponse(GetAllPlantsResponse value) {
        return new JAXBElement<GetAllPlantsResponse>(_GetAllPlantsResponse_QNAME, GetAllPlantsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlantResourceList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.soap.rentit9.esi/", name = "plants")
    public JAXBElement<PlantResourceList> createPlants(PlantResourceList value) {
        return new JAXBElement<PlantResourceList>(_Plants_QNAME, PlantResourceList.class, null, value);
    }

}
