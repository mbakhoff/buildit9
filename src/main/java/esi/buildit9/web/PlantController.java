package esi.buildit9.web;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import esi.buildit9.dto.PlantQueryDTO;
import esi.buildit9.soap.client.*;

@RequestMapping("/plants/**")
@Controller
public class PlantController {
	@Autowired
    private PlantSoapService service;
	
    @RequestMapping
    public String index(Model uiModel) {
    	List<PlantResource> plants = service.getAllPlants();
    	addDateTimeFormatPatterns(uiModel);
    	uiModel.addAttribute("plantsAvailableRequest",new PlantQueryDTO());
    	uiModel.addAttribute("plants", plants);
        return "plants/index";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String search(@ModelAttribute("plantsAvailableRequest") PlantQueryDTO query,Model uiModel) {
    	PlantsAvailableRequest req=new PlantsAvailableRequest();
    	req.setNameLike(query.getNameLike());
    	req.setStartDate(convert(query.getEndDate()));
    	req.setEndDate(convert(query.getEndDate()));
    	
    	List<PlantResource> plants = service.getPlantsBetween(req);
    	
    	addDateTimeFormatPatterns(uiModel);
    	uiModel.addAttribute("plantsAvailableRequest",new PlantQueryDTO());
    	uiModel.addAttribute("plants", plants);
        
    	return "plants/index";
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
