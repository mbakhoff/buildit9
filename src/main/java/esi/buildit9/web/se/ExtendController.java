package esi.buildit9.web.se;
import esi.buildit9.domain.Extend;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/se")
@Controller
@RooWebScaffold(path = "se", formBackingObject = Extend.class, delete = false)
public class ExtendController {
	
	
	
}
