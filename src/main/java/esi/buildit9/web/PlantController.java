package esi.buildit9.web;


import esi.buildit9.RBAC;
import esi.buildit9.domain.OrderStatus;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RentIt;
import esi.buildit9.domain.Site;
import esi.buildit9.dto.CreatePurchaseOrderDTO;
import esi.buildit9.dto.PlantLineDTO;
import esi.buildit9.interop.RentitInterop;
import org.joda.time.DateMidnight;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@RequestMapping("/plants/**")
@Controller
public class PlantController {
    //@Autowired
    //private PlantSoapService service;

    @RequestMapping
    public String index(Model uiModel) {

        CreatePurchaseOrderDTO dto = new CreatePurchaseOrderDTO();

        addCommonObjects(uiModel);
        uiModel.addAttribute("createPurchaseOrderForm", dto);

        return "plants/index";
    }

    @RequestMapping(params = "search", method = RequestMethod.POST)
    public String search(@ModelAttribute("createPurchaseOrderForm") CreatePurchaseOrderDTO dto, Model uiModel) {

        RentitInterop interop= dto.getRentIt().getInterop();

        List<esi.buildit9.rest.PlantResource> plants =
                interop.getAvailablePlantsBetween(dto.getNameLike(),dto.getStartDate(),dto.getEndDate());

        dto.setLinesFromPlants(plants);

        addCommonObjects(uiModel);
        uiModel.addAttribute("createPurchaseOrderForm", dto);
        return "plants/index";
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(@ModelAttribute("createPurchaseOrderForm") CreatePurchaseOrderDTO dto, Model uiModel) {
        List<String> errors = validate(dto);
        if (errors.size() > 0) {
            addCommonObjects(uiModel);
            uiModel.addAttribute("errors", formatErrors(errors));
            uiModel.addAttribute("createPurchaseOrderForm", dto);
            return "plants/index";
        }

        PurchaseOrder po = fromResource(dto);
        po.persist();

        if (RBAC.hasAuthority(RBAC.ROLE_ADMIN)) {
            return "redirect:/purchaseorders/" + po.getId();
        } else if (RBAC.hasAuthority(RBAC.ROLE_WORKS_ENGINEER)) {
            return "redirect:/we/po/" + po.getId();
        } else {
            return "redirect:/se/po/" + po.getId();
        }
    }

    private static PurchaseOrder fromResource(CreatePurchaseOrderDTO dto) {
        PlantLineDTO selectedLine = dto.getSearchLines().get(dto.getSelectedItem());

        PurchaseOrder order = new PurchaseOrder();
        order.setOrderStatus(OrderStatus.CREATED);
        order.setPlantExternalId(selectedLine.getId().toString());
        order.setPlantName(selectedLine.getName());
        order.setStartDate(dto.getStartDate());
        order.setEndDate(dto.getEndDate());
        order.setTotalPrice(calculateDuration(dto) * selectedLine.getPrice());
        order.setSite(Site.getOrCreateSite(dto.getSiteAddress()));
        order.setRentit(dto.getRentIt());
        order.setWorksEngineerName(dto.getWorksEngineerName());
        order.setSiteEngineerName(RBAC.getUser());
        return order;
    }

    private List<String> validate(CreatePurchaseOrderDTO dto) {
        List<String> errors = new ArrayList<String>();
        if (dto.getSelectedItem() == null) {
            errors.add("You must select an item");
        }
        if (dto.getStartDate() == null || dto.getEndDate() == null ||
                dto.getSiteAddress() == null || dto.getSiteAddress().length() == 0 ||
                dto.getWorksEngineerName() == null || dto.getWorksEngineerName().length() == 0) {
            errors.add("You must fill all the fields");
        }
        return errors;
    }

    private static String formatErrors(List<String> errors) {
        StringBuilder sb = new StringBuilder();
        for (String error : errors) {
            sb.append(error).append("<br/>");
        }
        return sb.toString();
    }

    private static int calculateDuration(CreatePurchaseOrderDTO dto) {
        return Days.daysBetween(new DateMidnight(dto.getStartDate()), new DateMidnight(dto.getEndDate())).getDays();
    }

    private static void addCommonObjects(Model uiModel) {
        uiModel.addAttribute("addedStart", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("addedEnd", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("plantsAvailableEndDate", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("plantsAvailableStartDate", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("sites", Site.findAllSites());
        uiModel.addAttribute("rentits", RentIt.findAllRentIts());
    }

    private static XMLGregorianCalendar convert(Calendar calendar) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(calendar.getTimeInMillis());
        XMLGregorianCalendar xmlCal = null;
        try {
            xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception ee) {
            ee.printStackTrace();
        }

        return xmlCal;
    }
}
