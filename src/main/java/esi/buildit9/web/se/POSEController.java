package esi.buildit9.web.se;
import esi.buildit9.domain.PurchaseOrder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/se/po")
@Controller
@RooWebScaffold(path = "se/po", formBackingObject = PurchaseOrder.class, delete = false)
public class POSEController {

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
		if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("purchaseorders", PurchaseOrder.findPurchaseOrdersBySiteEngineerNameEquals(name).getResultList());
            float nrOfPages = (float) PurchaseOrder.countPurchaseOrders() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("purchaseorders", PurchaseOrder.findPurchaseOrdersBySiteEngineerNameEquals(name).getResultList());
        }
        return "se/po/list";
    }
	
}
