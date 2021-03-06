// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.web;

import esi.buildit9.security.Authorities;
import esi.buildit9.web.Authoroties;
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

privileged aspect Authoroties_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String Authoroties.create(@Valid Authorities authorities, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, authorities);
            return "security/authoroties/create";
        }
        uiModel.asMap().clear();
        authorities.persist();
        return "redirect:/security/authoroties/" + encodeUrlPathSegment(authorities.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String Authoroties.createForm(Model uiModel) {
        populateEditForm(uiModel, new Authorities());
        return "security/authoroties/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String Authoroties.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("authorities", Authorities.findAuthorities(id));
        uiModel.addAttribute("itemId", id);
        return "security/authoroties/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String Authoroties.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("authoritieses", Authorities.findAuthoritiesEntries(firstResult, sizeNo));
            float nrOfPages = (float) Authorities.countAuthoritieses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("authoritieses", Authorities.findAllAuthoritieses());
        }
        return "security/authoroties/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String Authoroties.update(@Valid Authorities authorities, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, authorities);
            return "security/authoroties/update";
        }
        uiModel.asMap().clear();
        authorities.merge();
        return "redirect:/security/authoroties/" + encodeUrlPathSegment(authorities.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String Authoroties.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Authorities.findAuthorities(id));
        return "security/authoroties/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String Authoroties.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Authorities authorities = Authorities.findAuthorities(id);
        authorities.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/security/authoroties";
    }
    
    void Authoroties.populateEditForm(Model uiModel, Authorities authorities) {
        uiModel.addAttribute("authorities", authorities);
    }
    
    String Authoroties.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
