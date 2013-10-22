package esi.buildit9.dto;

import org.joda.time.DateTime;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class PlantsQuery {
    private DateTime startdate;
    private DateTime enddate;
    private String name;
}
