package esi.buildit9.web.se;
import esi.buildit9.domain.PurchaseOrder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/se/po")
@Controller
@RooWebScaffold(path = "se/po", formBackingObject = PurchaseOrder.class, delete = false)
public class POSEController {
}
