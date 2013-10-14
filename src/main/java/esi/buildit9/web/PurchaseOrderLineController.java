package esi.buildit9.web;
import esi.buildit9.domain.PurchaseOrderLine;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/purchaseorderlines")
@Controller
@RooWebScaffold(path = "purchaseorderlines", formBackingObject = PurchaseOrderLine.class)
public class PurchaseOrderLineController {
}
