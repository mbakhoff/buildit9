package esi.buildit9.web;


import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RentIt;
import esi.buildit9.domain.Site;
import esi.buildit9.dto.CreatePurchaseOrderDTO;
import esi.buildit9.dto.PlantLineDTO;
import esi.buildit9.soap.client.PlantResource;
import esi.buildit9.soap.client.PlantSoapService;
import esi.buildit9.soap.client.PlantsAvailableRequest;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@RequestMapping("/plants/**")
@Controller
public class PlantController {
	@Autowired
    private PlantSoapService service;
	
    @RequestMapping
    public String index(Model uiModel) {
    	List<PlantResource> plants = service.getAllPlants();

        CreatePurchaseOrderDTO dto = new CreatePurchaseOrderDTO();
        dto.setLinesFromPlants(plants);


        addCommonObjects(uiModel);
        uiModel.addAttribute("createPurchaseOrderForm",dto);

        return "plants/index";
    }

    @RequestMapping(params = "update", method = RequestMethod.GET)
    public String updateForm(Model uiModel){

        return "se/po/updateForm";
    }

    @RequestMapping(params = "search", method = RequestMethod.POST)
    public String search(@ModelAttribute("createPurchaseOrderForm") CreatePurchaseOrderDTO dto,
                         Model uiModel) {

        PlantsAvailableRequest req=new PlantsAvailableRequest();
    	req.setNameLike(dto.getNameLike());
    	req.setStartDate(convert(dto.getEndDate()));
    	req.setEndDate(convert(dto.getEndDate()));

    	List<PlantResource> plants = service.getPlantsBetween(req);
        dto.setLinesFromPlants(plants);

    	addCommonObjects(uiModel);
        uiModel.addAttribute("createPurchaseOrderForm",dto);
    	return "plants/index";
    }

    @RequestMapping(params = "addLines", method = RequestMethod.POST)
    public String addLines(@ModelAttribute("createPurchaseOrderForm") CreatePurchaseOrderDTO dto,
                           Model uiModel) {
        addSelectedPlant(dto);

        addCommonObjects(uiModel);
        uiModel.addAttribute("createPurchaseOrderForm", dto);
        return "plants/index";
    }

    @RequestMapping(params = "create",method = RequestMethod.POST)
    public String create(@ModelAttribute("createPurchaseOrderForm") CreatePurchaseOrderDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();



        PurchaseOrder po = new PurchaseOrder();
        po.setOrderStatus(dto.getOrderStatus());
        po.setPlantExternalId(dto.getSelectedPlantId());
        po.setPlantName(dto.getSelectedPlantName());
        po.setStartDate(dto.getStartDate());
        po.setEndDate(dto.getEndDate());
        po.setSite(Site.getOrCreateSite(dto.getSiteAddress()));
        po.setRentit(RentIt.getOrCreateRentIt("Rentit9"));//TODO:Remove hard coded for integration
        po.setWorksEngineerName(dto.getWorksEngineerName());
        po.setSiteEngineerName(authentication.getName());
        po.persist();

        return "redirect:/purchaseorders";
    }

    private void addCommonObjects(Model uiModel){
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("orderstatuses", Arrays.asList(esi.buildit9.domain.OrderStatus.values()));
        uiModel.addAttribute("sites", Site.findAllSites());
    }

    private void addDateTimeFormatPatterns(Model uiModel){
        uiModel.addAttribute("addedStart", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("addedEnd", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("plantsAvailableEndDate", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("plantsAvailableStartDate", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }

    private void addSelectedPlant(CreatePurchaseOrderDTO dto) {
        for (PlantLineDTO pl : dto.getSearchLines()) {
            if (pl.getChecked()) {
                dto.setSelectedPlantId(pl.getId().toString());
                dto.setSelectedPlantName(pl.getName());
            }
        }
    }

    private XMLGregorianCalendar convert(Calendar calendar){
    	GregorianCalendar cal=new GregorianCalendar();
    	cal.setTimeInMillis(calendar.getTimeInMillis());
    	XMLGregorianCalendar xmlCal = null;
    	try{
    		xmlCal=DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
    	}
    	catch(Exception ee){
    		ee.printStackTrace();
    	}

    	return xmlCal;
    }
}
