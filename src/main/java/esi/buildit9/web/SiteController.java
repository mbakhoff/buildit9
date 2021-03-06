package esi.buildit9.web;
import esi.buildit9.domain.Site;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/sites")
@Controller
@RooWebScaffold(path = "sites", formBackingObject = Site.class)
public class SiteController {
}
