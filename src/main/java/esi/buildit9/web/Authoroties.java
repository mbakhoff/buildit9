package esi.buildit9.web;
import esi.buildit9.security.Authorities;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/security/authoroties")
@Controller
@RooWebScaffold(path = "security/authoroties", formBackingObject = Authorities.class)
public class Authoroties {
}
