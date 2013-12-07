package esi.buildit9.domain;

import esi.buildit9.interop.InteropImplementation;
import esi.buildit9.interop.RentitInterop;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findRentItsByNameEquals" })
public class RentIt {

    /**
     */
    private String name;

    private InteropImplementation provider;

    public static RentIt getOrCreateRentIt(String name) {
        RentIt rentIt;
        try{
            rentIt = findRentItsByNameEquals(name).getSingleResult();
        }
        catch (DataRetrievalFailureException ee){
            rentIt=new RentIt();
            rentIt.setName(name);
            rentIt.persist();

        }
        return rentIt;
    }

    public RentitInterop getInterop() {
        if (provider == null) {
            System.err.println("warning: no interop defined for rentit " + getName());
            return InteropImplementation.Dummy.getImpl();
        }
        return provider.getImpl();
    }
}
