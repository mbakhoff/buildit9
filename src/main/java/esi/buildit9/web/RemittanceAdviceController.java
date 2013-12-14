package esi.buildit9.web;
import esi.buildit9.domain.RemittanceAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("/remittanceadvices")
@Controller
@RooWebScaffold(path = "remittanceadvices", formBackingObject = RemittanceAdvice.class)
public class RemittanceAdviceController {

    @Autowired
    private ApplicationContext ctx;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid RemittanceAdvice remittanceAdvice,
                                                    BindingResult bindingResult, Model uiModel,
                                                    HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, remittanceAdvice);
            return "remittanceadvices/create";
        }
        uiModel.asMap().clear();
        remittanceAdvice.persist();

        remittanceAdvice.getInvoice().getPurchaseOrder().getRentit().getInterop().submitRemittanceAdvice(ctx, remittanceAdvice);

        return "redirect:/remittanceadvices/" + encodeUrlPathSegment(remittanceAdvice.getId().toString(), httpServletRequest);
    }
}
