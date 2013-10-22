
package esi.buildit9.soap.client;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "PlantSoapService", targetNamespace = "http://web.soap.rentit9.esi/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface PlantSoapService {


    /**
     * 
     * @return
     *     returns esi.buildit9.soap.client.PlantListResource
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAllPlants", targetNamespace = "http://web.soap.rentit9.esi/", className = "esi.buildit9.soap.client.GetAllPlants")
    @ResponseWrapper(localName = "getAllPlantsResponse", targetNamespace = "http://web.soap.rentit9.esi/", className = "esi.buildit9.soap.client.GetAllPlantsResponse")
    @Action(input = "http://web.soap.rentit9.esi/PlantSoapService/getAllPlantsRequest", output = "http://web.soap.rentit9.esi/PlantSoapService/getAllPlantsResponse")
    public PlantListResource getAllPlants();

}