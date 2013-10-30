package esi.buildit9.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import esi.buildit9.soap.client.*;

@RequestMapping("/plants/**")
@Controller
public class PlantController {
	@Autowired
    private PlantSoapService service;
	
    @RequestMapping(method = RequestMethod.POST, value = "{id}")
    public void post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }

    @RequestMapping
    public String index(Model uiModel) {
    	List<PlantResource> plants = service.getAllPlants();
    	
    	
    	uiModel.addAttribute("plants", plants);
        return "plants/index";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String search(@Valid PlantsAvailableRequest query,Model uiModel) {

    	List<PlantResource> plants = service.getPlantsBetween(query);

    	uiModel.addAttribute("plants", plants);
        return "plants/index";
    }
}
