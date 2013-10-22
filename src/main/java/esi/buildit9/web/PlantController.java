package esi.buildit9.web;

import esi.buildit9.dto.PlantsQuery;
import esi.buildit9.soap.client.PlantResource;
import esi.buildit9.soap.client.PlantSoapService;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/plants/**")
@Controller
public class PlantController {

    @Autowired private PlantSoapService plantService;

    @RequestMapping
    public String index() {
        List<PlantResource> plants = plantService.getAllPlants().getPlant();

        return "plant/index";
    }

    @RequestMapping
    public String search(ModelMap modelMap){
        addDateTimeFormatPatterns(modelMap);
        modelMap.addAttribute("plantsquery",new PlantsQuery());

        return "plant/search";
    }

    void addDateTimeFormatPatterns(ModelMap modelMap) {
        modelMap.put("plantHRBean_startr_date_format",
                DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        modelMap.put("plantHRBean_endr_date_format",
                DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }
}
