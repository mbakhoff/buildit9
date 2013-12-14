package esi.buildit9.web;

import java.util.ArrayList;
import java.util.List;

import esi.buildit9.domain.OrderStatus;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.interop.RentitInterop;

import org.joda.time.format.ISODateTimeFormat;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("/purchaseorders")
@Controller
@RooWebScaffold(path = "purchaseorders", formBackingObject = PurchaseOrder.class)
public class PurchaseOrderController {

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid PurchaseOrder purchaseOrder, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, purchaseOrder);
            return "purchaseorders/update";
        }

        PurchaseOrder cleanCopy = PurchaseOrder.findPurchaseOrder(purchaseOrder.getId());
        boolean statusChanged = cleanCopy.getOrderStatus() != purchaseOrder.getOrderStatus();

        if (statusChanged && purchaseOrder.getOrderStatus() == OrderStatus.APPROVED) {
            RentitInterop interop = purchaseOrder.getRentit().getInterop();
            interop.submitOrder(purchaseOrder);
        }

        uiModel.asMap().clear();
        purchaseOrder.merge();
        return "redirect:/purchaseorders/" + encodeUrlPathSegment(purchaseOrder.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/all", produces = "text/html")
    public String all(Model uiModel) {
    	addDeliverables(uiModel, PurchaseOrder.getPOsForWorkers());
        return "purchaseorders/all";
    }
    
    private static void addDeliverables(Model uiModel, List<PurchaseOrder> ordersForWorkers) {
        List<PurchaseOrder> deliveries = new ArrayList<PurchaseOrder>();
        for (PurchaseOrder orderLine : ordersForWorkers) {
            deliveries.add(orderLine);
        }
        uiModel.addAttribute("plants", deliveries);
    }
}
