package esi.buildit9.domain;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = {"findSitesByAddressEquals"})
public class Site {

    /**
     */
    private String address;

    public static Site getOrCreateSite(String siteAddress) {
        Site site;
        try {
            site = findSitesByAddressEquals(siteAddress).getSingleResult();
        } catch (DataRetrievalFailureException ee) {
            site = new Site();
            site.setAddress(siteAddress);
            site.persist();
        }
        return site;
    }
}
