package esi.buildit9.web.we;
import esi.buildit9.domain.OrderStatus;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.interop.RentitInterop;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

        PurchaseOrder cleanCopy = PurchaseOrder.findPurchaseOrder(purchaseOrder.getId());
        boolean statusChanged = cleanCopy.getOrderStatus() != purchaseOrder.getOrderStatus();
		purchaseOrder.merge();

        if (statusChanged && purchaseOrder.getOrderStatus() == OrderStatus.APPROVED) {
            RentitInterop interop = purchaseOrder.getRentit().getInterop();
            interop.submitOrder(purchaseOrder);
        }

        uiModel.asMap().clear();
        return "redirect:/we/po/" + encodeUrlPathSegment(purchaseOrder.getId().toString(), httpServletRequest);
	}
	
}
