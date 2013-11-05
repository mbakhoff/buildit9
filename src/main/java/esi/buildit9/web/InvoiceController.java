package esi.buildit9.web;
import esi.buildit9.domain.Invoice;
import esi.buildit9.domain.InvoiceStatus;
import esi.buildit9.service.InvoiceHelper;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("/invoices")
@Controller
@RooWebScaffold(path = "invoices", formBackingObject = Invoice.class)
public class InvoiceController {

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Invoice submitted, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, submitted);
            return "invoices/update";
        }

        Invoice stored = Invoice.findInvoice(submitted.getId());

        uiModel.asMap().clear();
        submitted.merge();

        if (wasApproved(submitted, stored)) {
            InvoiceHelper.createAndSendRemittanceAdvice(submitted);
        }
        if (wasRejected(submitted, stored)) {
            InvoiceHelper.sendManuallyRejected(submitted);
        }
        return "redirect:/invoices/" + encodeUrlPathSegment(submitted.getId().toString(), httpServletRequest);
    }

    private static boolean wasRejected(Invoice submitted, Invoice stored) {
        return stored.getStatus() != InvoiceStatus.REJECTED && submitted.getStatus() == InvoiceStatus.REJECTED;
    }

    private static boolean wasApproved(Invoice submitted, Invoice stored) {
        return stored.getStatus() != InvoiceStatus.APPROVED && submitted.getStatus() == InvoiceStatus.APPROVED;
    }

}
