package esi.buildit9.web;
import esi.buildit9.domain.PlantHireRequestLine;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/planthirerequestlines")
@Controller
@RooWebScaffold(path = "planthirerequestlines", formBackingObject = PlantHireRequestLine.class)
public class PlantHireRequestLineController {
}
