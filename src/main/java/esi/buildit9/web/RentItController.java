package esi.buildit9.web;
import esi.buildit9.domain.RentIt;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/rentits")
@Controller
@RooWebScaffold(path = "rentits", formBackingObject = RentIt.class)
public class RentItController {
}
