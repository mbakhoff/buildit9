// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.web;

import esi.buildit9.security.AssignmentsDerp;
import esi.buildit9.security.Authorities;
import esi.buildit9.security.Users;
import esi.buildit9.web.Assignments;
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

privileged aspect Assignments_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String Assignments.create(@Valid AssignmentsDerp assignmentsDerp, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, assignmentsDerp);
            return "security/assignments/create";
        }
        uiModel.asMap().clear();
        assignmentsDerp.persist();
        return "redirect:/security/assignments/" + encodeUrlPathSegment(assignmentsDerp.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String Assignments.createForm(Model uiModel) {
        populateEditForm(uiModel, new AssignmentsDerp());
        return "security/assignments/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String Assignments.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("assignmentsderp", AssignmentsDerp.findAssignmentsDerp(id));
        uiModel.addAttribute("itemId", id);
        return "security/assignments/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String Assignments.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("assignmentsderps", AssignmentsDerp.findAssignmentsDerpEntries(firstResult, sizeNo));
            float nrOfPages = (float) AssignmentsDerp.countAssignmentsDerps() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("assignmentsderps", AssignmentsDerp.findAllAssignmentsDerps());
        }
        return "security/assignments/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String Assignments.update(@Valid AssignmentsDerp assignmentsDerp, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, assignmentsDerp);
            return "security/assignments/update";
        }
        uiModel.asMap().clear();
        assignmentsDerp.merge();
        return "redirect:/security/assignments/" + encodeUrlPathSegment(assignmentsDerp.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String Assignments.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, AssignmentsDerp.findAssignmentsDerp(id));
        return "security/assignments/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String Assignments.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        AssignmentsDerp assignmentsDerp = AssignmentsDerp.findAssignmentsDerp(id);
        assignmentsDerp.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/security/assignments";
    }
    
    void Assignments.populateEditForm(Model uiModel, AssignmentsDerp assignmentsDerp) {
        uiModel.addAttribute("assignmentsDerp", assignmentsDerp);
        uiModel.addAttribute("authoritieses", Authorities.findAllAuthoritieses());
        uiModel.addAttribute("userses", Users.findAllUserses());
    }
    
    String Assignments.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
