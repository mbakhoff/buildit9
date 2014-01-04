package esi.buildit9.web.se;
import esi.buildit9.RBAC;
import esi.buildit9.domain.OrderStatus;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.interop.RentitInterop;
import org.joda.time.DateMidnight;
import org.joda.time.Days;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static org.joda.time.Days.*;

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
            uiModel.addAttribute("purchaseorders", PurchaseOrder.findPurchaseOrdersBySiteEngineerNameEquals(name).setFirstResult(firstResult).setMaxResults(sizeNo).getResultList() );
            float nrOfPages = (float) PurchaseOrder.countPurchaseOrders() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("purchaseorders", PurchaseOrder.findPurchaseOrdersBySiteEngineerNameEquals(name).getResultList());
        }
        return "se/po/list";
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setSiteEngineerName(RBAC.getUser());
        populateEditForm(uiModel, purchaseOrder);
        return "se/po/create";
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid PurchaseOrder purchaseOrder, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        checkOrderStatus(purchaseOrder, bindingResult);
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, purchaseOrder);
            return "se/po/create";
        }
        uiModel.asMap().clear();
        purchaseOrder.persist();
        return "redirect:/se/po/" + encodeUrlPathSegment(purchaseOrder.getId().toString(), httpServletRequest);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid PurchaseOrder purchaseOrder, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        checkIfValidOrderUpdate(purchaseOrder, bindingResult);
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, purchaseOrder);
            return "se/po/update";
        }
        if (Days.daysBetween(new DateMidnight(purchaseOrder.getStartDate()), new DateMidnight()).getDays() <= 1){
            throw new IllegalArgumentException("Rejecting the Purchase Order is not allowed!" +
                    "Cancellations are allowed until the day before the plant is due to be delivered!");
        } else if (purchaseOrder.getOrderStatus() == OrderStatus.APPROVED ||
                purchaseOrder.getOrderStatus() == OrderStatus.RENTIT_CONFIRMED) {
            RentitInterop provider = purchaseOrder.getRentit().getInterop();
            provider.cancelOrder(purchaseOrder);
        }
        uiModel.asMap().clear();
        purchaseOrder.merge();
        return "redirect:/se/po/" + encodeUrlPathSegment(purchaseOrder.getId().toString(), httpServletRequest);
    }

    private static void checkOrderStatus(PurchaseOrder purchaseOrder, BindingResult bindingResult) {
        final List<OrderStatus> allowedStatuses = Arrays.asList(
                OrderStatus.CREATED,
                OrderStatus.WAITING_APPROVAL,
                OrderStatus.CLOSED);
        if (!allowedStatuses.contains(purchaseOrder.getOrderStatus())) {
            bindingResult.reject(null, "Illegal order status");
        }
    }

    private void checkIfValidOrderUpdate(PurchaseOrder purchaseOrder, BindingResult bindingResult) {
        PurchaseOrder oldState = PurchaseOrder.findPurchaseOrder(purchaseOrder.getId());
        if (oldState.getOrderStatus() != OrderStatus.CREATED) {
            bindingResult.reject(null, "SEs can only update orders in status Created");
        }
        checkOrderStatus(purchaseOrder, bindingResult);
    }
}
