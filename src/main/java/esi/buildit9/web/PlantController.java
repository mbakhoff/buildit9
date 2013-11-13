package esi.buildit9.web;


import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import esi.buildit9.domain.*;
import esi.buildit9.dto.AddLinesDTO;
import esi.buildit9.dto.CreatePurchaseOrderDTO;
import esi.buildit9.dto.PlantLineDTO;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import esi.buildit9.dto.PlantQueryDTO;
import esi.buildit9.soap.client.*;

@RequestMapping("/plants/**")
@SessionAttributes({"createPurchaseOrderForm"})
@Controller
public class PlantController {
	@Autowired
    private PlantSoapService service;
	
    @RequestMapping
    public String index(Model uiModel) {
    	List<PlantResource> plants = service.getAllPlants();

        CreatePurchaseOrderDTO dto = new CreatePurchaseOrderDTO();

        dto.createFromPlants(plants);

        //PlantQueryDTO plantQueryDTO = new PlantQueryDTO();
        //dto.setPlantsQuery(plantQueryDTO);

        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("orderstatuses", Arrays.asList(esi.buildit9.domain.OrderStatus.values()));
        uiModel.addAttribute("createPurchaseOrderForm",dto);

        return "plants/index";
    }
    
    @RequestMapping(params = "search", method = RequestMethod.POST)
    public String search(@ModelAttribute("createPurchaseOrderForm") CreatePurchaseOrderDTO dto,
                         Model uiModel) {
    	PlantsAvailableRequest req=new PlantsAvailableRequest();
    	req.setNameLike(dto.getNameLike());
    	req.setStartDate(convert(dto.getEndDate()));
    	req.setEndDate(convert(dto.getEndDate()));
    	
    	List<PlantResource> plants = service.getPlantsBetween(req);

        //AddLinesDTO addLinesDTO = new AddLinesDTO();
        dto.createFromPlants(plants);
        //dto.setAddLines(addLinesDTO);

    	addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("orderstatuses", Arrays.asList(esi.buildit9.domain.OrderStatus.values()));

        uiModel.addAttribute("createPurchaseOrderForm",dto);
        
    	return "plants/index";
    }

    @RequestMapping(params = "addLines", method = RequestMethod.POST)
    public String addLines(@ModelAttribute("createPurchaseOrderForm") CreatePurchaseOrderDTO dto,
                           Model uiModel) {

        uiModel.addAttribute("createPurchaseOrderForm",dto);

        addPurchaseOrderLines(dto);

        uiModel.addAttribute("orderstatuses", Arrays.asList(esi.buildit9.domain.OrderStatus.values()));

        addDateTimeFormatPatterns(uiModel);

        return "plants/index";
    }

    @RequestMapping(params = "create",method = RequestMethod.POST)
    public String create(@ModelAttribute("createPurchaseOrderForm") CreatePurchaseOrderDTO dto,
                           Model uiModel){
        //TODO: implement creating of PurchaseOrder


        return "purchaseorders/index";
    }


    private void addPurchaseOrderLines(CreatePurchaseOrderDTO dto) {

        Calendar startDate = dto.getStartDate();
        Calendar endDate = dto.getEndDate();
        setToNow(startDate,endDate);


        for (PlantLineDTO pl:dto.getSearchLines()){
            if (pl.getChecked()==true){
                PurchaseOrderLine pr =new PurchaseOrderLine();
                pr.setPlantExternalId(pl.getId().toString());
                pr.setPlantName(pl.getName());
                pr.setStartDate(startDate);
                pr.setEndDate(endDate);
                dto.getAddedLines().add(pr);
            }
        }

    }

    private void setToNow(Calendar startDate, Calendar endDate) {
        if (startDate==null){
            startDate=Calendar.getInstance();
        }
        if (endDate==null){
            endDate=Calendar.getInstance();
        }
    }

    XMLGregorianCalendar convert(Calendar calendar){
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
    
    void addDateTimeFormatPatterns(Model uiModel){
    	uiModel.addAttribute("plantsAvailableStartDate", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    	uiModel.addAttribute("plantsAvailableEndDate", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }
}
