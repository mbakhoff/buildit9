// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.web.se;

import esi.buildit9.domain.OrderStatus;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.PurchaseOrderLine;
import esi.buildit9.domain.RentIt;
import esi.buildit9.domain.Site;
import esi.buildit9.web.se.POSEController;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect POSEController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String POSEController.create(@Valid PurchaseOrder purchaseOrder, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, purchaseOrder);
            return "se/po/create";
        }
        uiModel.asMap().clear();
        purchaseOrder.persist();
        return "redirect:/se/po/" + encodeUrlPathSegment(purchaseOrder.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String POSEController.createForm(Model uiModel) {
        populateEditForm(uiModel, new PurchaseOrder());
        return "se/po/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String POSEController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("purchaseorder", PurchaseOrder.findPurchaseOrder(id));
        uiModel.addAttribute("itemId", id);
        return "se/po/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String POSEController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("purchaseorders", PurchaseOrder.findPurchaseOrderEntries(firstResult, sizeNo));
            float nrOfPages = (float) PurchaseOrder.countPurchaseOrders() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("purchaseorders", PurchaseOrder.findAllPurchaseOrders());
        }
        return "se/po/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String POSEController.update(@Valid PurchaseOrder purchaseOrder, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, purchaseOrder);
            return "se/po/update";
        }
        uiModel.asMap().clear();
        purchaseOrder.merge();
        return "redirect:/se/po/" + encodeUrlPathSegment(purchaseOrder.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String POSEController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, PurchaseOrder.findPurchaseOrder(id));
        return "se/po/update";
    }
    
    void POSEController.populateEditForm(Model uiModel, PurchaseOrder purchaseOrder) {
        uiModel.addAttribute("purchaseOrder", purchaseOrder);
        uiModel.addAttribute("orderstatuses", Arrays.asList(OrderStatus.values()));
        uiModel.addAttribute("purchaseorderlines", PurchaseOrderLine.findAllPurchaseOrderLines());
        uiModel.addAttribute("rentits", RentIt.findAllRentIts());
        uiModel.addAttribute("sites", Site.findAllSites());
    }
    
    String POSEController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
