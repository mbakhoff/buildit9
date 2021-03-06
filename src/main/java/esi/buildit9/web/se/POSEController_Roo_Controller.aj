// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.web.se;

import esi.buildit9.domain.OrderStatus;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RentIt;
import esi.buildit9.domain.Site;
import esi.buildit9.web.se.POSEController;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect POSEController_Roo_Controller {
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String POSEController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("purchaseorder", PurchaseOrder.findPurchaseOrder(id));
        uiModel.addAttribute("itemId", id);
        return "se/po/show";
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String POSEController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, PurchaseOrder.findPurchaseOrder(id));
        return "se/po/update";
    }
    
    void POSEController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("purchaseOrder_startdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("purchaseOrder_enddate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    
    void POSEController.populateEditForm(Model uiModel, PurchaseOrder purchaseOrder) {
        uiModel.addAttribute("purchaseOrder", purchaseOrder);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("orderstatuses", Arrays.asList(OrderStatus.values()));
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
