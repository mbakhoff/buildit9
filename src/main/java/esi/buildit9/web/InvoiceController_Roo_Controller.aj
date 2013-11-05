// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.web;

import esi.buildit9.domain.Invoice;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RentIt;
import esi.buildit9.web.InvoiceController;
import java.io.UnsupportedEncodingException;
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

privileged aspect InvoiceController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String InvoiceController.create(@Valid Invoice invoice, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, invoice);
            return "invoices/create";
        }
        uiModel.asMap().clear();
        invoice.persist();
        return "redirect:/invoices/" + encodeUrlPathSegment(invoice.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String InvoiceController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Invoice());
        return "invoices/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String InvoiceController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("invoice", Invoice.findInvoice(id));
        uiModel.addAttribute("itemId", id);
        return "invoices/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String InvoiceController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("invoices", Invoice.findInvoiceEntries(firstResult, sizeNo));
            float nrOfPages = (float) Invoice.countInvoices() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("invoices", Invoice.findAllInvoices());
        }
        return "invoices/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String InvoiceController.update(@Valid Invoice invoice, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, invoice);
            return "invoices/update";
        }
        uiModel.asMap().clear();
        invoice.merge();
        return "redirect:/invoices/" + encodeUrlPathSegment(invoice.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String InvoiceController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Invoice.findInvoice(id));
        return "invoices/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String InvoiceController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Invoice invoice = Invoice.findInvoice(id);
        invoice.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/invoices";
    }
    
    void InvoiceController.populateEditForm(Model uiModel, Invoice invoice) {
        uiModel.addAttribute("invoice", invoice);
        uiModel.addAttribute("purchaseorders", PurchaseOrder.findAllPurchaseOrders());
        uiModel.addAttribute("rentits", RentIt.findAllRentIts());
    }
    
    String InvoiceController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
