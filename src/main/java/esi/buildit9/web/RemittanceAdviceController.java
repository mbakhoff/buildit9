package esi.buildit9.web;
import esi.buildit9.domain.RemittanceAdvice;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/remittanceadvices")
@Controller
@RooWebScaffold(path = "remittanceadvices", formBackingObject = RemittanceAdvice.class)
public class RemittanceAdviceController {
}
