package esi.buildit9.web.we;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import esi.buildit9.domain.PurchaseOrder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/we/po")
@Controller
@RooWebScaffold(path = "we/po", formBackingObject = PurchaseOrder.class, create = false)
public class POWEController {
	
	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid PurchaseOrder purchaseOrder, BindingResult	bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
	
		if (bindingResult.hasErrors()) { 
			populateEditForm(uiModel, purchaseOrder);
			return "we/po/update";
		}
		
		PurchaseOrder originalResource = PurchaseOrder.findPurchaseOrder(purchaseOrder.getId());
		originalResource.setOrderStatus(purchaseOrder.getOrderStatus());
		
		uiModel.asMap().clear();
		originalResource.merge();
		
		return "redirect:/we/po/" + encodeUrlPathSegment(originalResource.getId().toString(), httpServletRequest);
		}
	
}
