package esi.buildit9.web;

import esi.buildit9.domain.OrderStatus;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.interop.RemoteHostException;
import esi.buildit9.interop.RentitInterop;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    
    @RequestMapping(value = "/listall", produces = "text/html")
    public String all(Model uiModel) {
    	addDeliverables(uiModel, PurchaseOrder.getPOsForWorkers());
        return "purchaseorders/listall";
    }
    
    private static void addDeliverables(Model uiModel, List<PurchaseOrder> ordersForWorkers) {
        List<PurchaseOrder> deliveries = new ArrayList<PurchaseOrder>();
        for (PurchaseOrder orderLine : ordersForWorkers) {
            deliveries.add(orderLine);
        }
        uiModel.addAttribute("purchaseordersall", deliveries);
    }

    @RequestMapping(value = "/extend")
    public String startExtension(Model uiModel) {
        uiModel.addAttribute("orders", PurchaseOrder.findAllPurchaseOrders());
        uiModel.addAttribute("extension", new ExtensionDto());
        return "purchaseorders/extend";
    }

    @RequestMapping(value = "/extend", method = RequestMethod.POST)
    public String tryExtend(@Valid ExtensionDto extension, BindingResult bindingResult, Model uiModel) {
        PurchaseOrder order = PurchaseOrder.findPurchaseOrder(extension.getOrderId());
        order.setEndDate(toCalendar(extension));
        try {
            order.getRentit().getInterop().extendOrder(order);
            order.merge();
            return "redirect:/purchaseorders/"+order.getId();
        } catch (RemoteHostException ex) {
            uiModel.addAttribute("orders", PurchaseOrder.findAllPurchaseOrders());
            uiModel.addAttribute("extension", extension);
            uiModel.addAttribute("error", ex.status + ":" + ex.getMessage());
            return "purchaseorders/extend";
        }
    }

    private Calendar toCalendar(ExtensionDto extension) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(extension.getEndDate());
        return cal;
    }

    public static class ExtensionDto {

        private Long orderId;
        private Date endDate;

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    }

}
