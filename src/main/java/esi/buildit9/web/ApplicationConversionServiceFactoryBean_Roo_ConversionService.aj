// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.web;

import esi.buildit9.domain.Invoice;
import esi.buildit9.domain.PurchaseOrder;
import esi.buildit9.domain.RemittanceAdvice;
import esi.buildit9.domain.RentIt;
import esi.buildit9.domain.Site;
import esi.buildit9.security.AssignmentsDerp;
import esi.buildit9.security.Authorities;
import esi.buildit9.security.Users;
import esi.buildit9.web.ApplicationConversionServiceFactoryBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    public Converter<Invoice, String> ApplicationConversionServiceFactoryBean.getInvoiceToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<esi.buildit9.domain.Invoice, java.lang.String>() {
            public String convert(Invoice invoice) {
                return new StringBuilder().append(invoice.getSenderEmail()).append(' ').append(invoice.getIdAtRentit()).toString();
            }
        };
    }
    
    public Converter<Long, Invoice> ApplicationConversionServiceFactoryBean.getIdToInvoiceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, esi.buildit9.domain.Invoice>() {
            public esi.buildit9.domain.Invoice convert(java.lang.Long id) {
                return Invoice.findInvoice(id);
            }
        };
    }
    
    public Converter<String, Invoice> ApplicationConversionServiceFactoryBean.getStringToInvoiceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, esi.buildit9.domain.Invoice>() {
            public esi.buildit9.domain.Invoice convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Invoice.class);
            }
        };
    }
    
    public Converter<PurchaseOrder, String> ApplicationConversionServiceFactoryBean.getPurchaseOrderToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<esi.buildit9.domain.PurchaseOrder, java.lang.String>() {
            public String convert(PurchaseOrder purchaseOrder) {
                return new StringBuilder().append(purchaseOrder.getPlantExternalId()).append(' ').append(purchaseOrder.getIdAtRentit()).append(' ').append(purchaseOrder.getPlantName()).append(' ').append(purchaseOrder.getTotalPrice()).toString();
            }
        };
    }
    
    public Converter<Long, PurchaseOrder> ApplicationConversionServiceFactoryBean.getIdToPurchaseOrderConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, esi.buildit9.domain.PurchaseOrder>() {
            public esi.buildit9.domain.PurchaseOrder convert(java.lang.Long id) {
                return PurchaseOrder.findPurchaseOrder(id);
            }
        };
    }
    
    public Converter<String, PurchaseOrder> ApplicationConversionServiceFactoryBean.getStringToPurchaseOrderConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, esi.buildit9.domain.PurchaseOrder>() {
            public esi.buildit9.domain.PurchaseOrder convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), PurchaseOrder.class);
            }
        };
    }
    
    public Converter<RemittanceAdvice, String> ApplicationConversionServiceFactoryBean.getRemittanceAdviceToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<esi.buildit9.domain.RemittanceAdvice, java.lang.String>() {
            public String convert(RemittanceAdvice remittanceAdvice) {
                return new StringBuilder().append(remittanceAdvice.getPayDay()).toString();
            }
        };
    }
    
    public Converter<Long, RemittanceAdvice> ApplicationConversionServiceFactoryBean.getIdToRemittanceAdviceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, esi.buildit9.domain.RemittanceAdvice>() {
            public esi.buildit9.domain.RemittanceAdvice convert(java.lang.Long id) {
                return RemittanceAdvice.findRemittanceAdvice(id);
            }
        };
    }
    
    public Converter<String, RemittanceAdvice> ApplicationConversionServiceFactoryBean.getStringToRemittanceAdviceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, esi.buildit9.domain.RemittanceAdvice>() {
            public esi.buildit9.domain.RemittanceAdvice convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), RemittanceAdvice.class);
            }
        };
    }
    
    public Converter<RentIt, String> ApplicationConversionServiceFactoryBean.getRentItToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<esi.buildit9.domain.RentIt, java.lang.String>() {
            public String convert(RentIt rentIt) {
                return new StringBuilder().append(rentIt.getName()).append(' ').append(rentIt.getEmail()).toString();
            }
        };
    }
    
    public Converter<Long, RentIt> ApplicationConversionServiceFactoryBean.getIdToRentItConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, esi.buildit9.domain.RentIt>() {
            public esi.buildit9.domain.RentIt convert(java.lang.Long id) {
                return RentIt.findRentIt(id);
            }
        };
    }
    
    public Converter<String, RentIt> ApplicationConversionServiceFactoryBean.getStringToRentItConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, esi.buildit9.domain.RentIt>() {
            public esi.buildit9.domain.RentIt convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), RentIt.class);
            }
        };
    }
    
    public Converter<Site, String> ApplicationConversionServiceFactoryBean.getSiteToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<esi.buildit9.domain.Site, java.lang.String>() {
            public String convert(Site site) {
                return new StringBuilder().append(site.getAddress()).toString();
            }
        };
    }
    
    public Converter<Long, Site> ApplicationConversionServiceFactoryBean.getIdToSiteConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, esi.buildit9.domain.Site>() {
            public esi.buildit9.domain.Site convert(java.lang.Long id) {
                return Site.findSite(id);
            }
        };
    }
    
    public Converter<String, Site> ApplicationConversionServiceFactoryBean.getStringToSiteConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, esi.buildit9.domain.Site>() {
            public esi.buildit9.domain.Site convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Site.class);
            }
        };
    }
    
    public Converter<AssignmentsDerp, String> ApplicationConversionServiceFactoryBean.getAssignmentsDerpToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<esi.buildit9.security.AssignmentsDerp, java.lang.String>() {
            public String convert(AssignmentsDerp assignmentsDerp) {
                return "(no displayable fields)";
            }
        };
    }
    
    public Converter<Long, AssignmentsDerp> ApplicationConversionServiceFactoryBean.getIdToAssignmentsDerpConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, esi.buildit9.security.AssignmentsDerp>() {
            public esi.buildit9.security.AssignmentsDerp convert(java.lang.Long id) {
                return AssignmentsDerp.findAssignmentsDerp(id);
            }
        };
    }
    
    public Converter<String, AssignmentsDerp> ApplicationConversionServiceFactoryBean.getStringToAssignmentsDerpConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, esi.buildit9.security.AssignmentsDerp>() {
            public esi.buildit9.security.AssignmentsDerp convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), AssignmentsDerp.class);
            }
        };
    }
    
    public Converter<Authorities, String> ApplicationConversionServiceFactoryBean.getAuthoritiesToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<esi.buildit9.security.Authorities, java.lang.String>() {
            public String convert(Authorities authorities) {
                return new StringBuilder().append(authorities.getAuthority()).toString();
            }
        };
    }
    
    public Converter<Long, Authorities> ApplicationConversionServiceFactoryBean.getIdToAuthoritiesConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, esi.buildit9.security.Authorities>() {
            public esi.buildit9.security.Authorities convert(java.lang.Long id) {
                return Authorities.findAuthorities(id);
            }
        };
    }
    
    public Converter<String, Authorities> ApplicationConversionServiceFactoryBean.getStringToAuthoritiesConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, esi.buildit9.security.Authorities>() {
            public esi.buildit9.security.Authorities convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Authorities.class);
            }
        };
    }
    
    public Converter<Users, String> ApplicationConversionServiceFactoryBean.getUsersToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<esi.buildit9.security.Users, java.lang.String>() {
            public String convert(Users users) {
                return new StringBuilder().append(users.getUsername()).append(' ').append(users.getPassword()).toString();
            }
        };
    }
    
    public Converter<Long, Users> ApplicationConversionServiceFactoryBean.getIdToUsersConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, esi.buildit9.security.Users>() {
            public esi.buildit9.security.Users convert(java.lang.Long id) {
                return Users.findUsers(id);
            }
        };
    }
    
    public Converter<String, Users> ApplicationConversionServiceFactoryBean.getStringToUsersConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, esi.buildit9.security.Users>() {
            public esi.buildit9.security.Users convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Users.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getInvoiceToStringConverter());
        registry.addConverter(getIdToInvoiceConverter());
        registry.addConverter(getStringToInvoiceConverter());
        registry.addConverter(getPurchaseOrderToStringConverter());
        registry.addConverter(getIdToPurchaseOrderConverter());
        registry.addConverter(getStringToPurchaseOrderConverter());
        registry.addConverter(getRemittanceAdviceToStringConverter());
        registry.addConverter(getIdToRemittanceAdviceConverter());
        registry.addConverter(getStringToRemittanceAdviceConverter());
        registry.addConverter(getRentItToStringConverter());
        registry.addConverter(getIdToRentItConverter());
        registry.addConverter(getStringToRentItConverter());
        registry.addConverter(getSiteToStringConverter());
        registry.addConverter(getIdToSiteConverter());
        registry.addConverter(getStringToSiteConverter());
        registry.addConverter(getAssignmentsDerpToStringConverter());
        registry.addConverter(getIdToAssignmentsDerpConverter());
        registry.addConverter(getStringToAssignmentsDerpConverter());
        registry.addConverter(getAuthoritiesToStringConverter());
        registry.addConverter(getIdToAuthoritiesConverter());
        registry.addConverter(getStringToAuthoritiesConverter());
        registry.addConverter(getUsersToStringConverter());
        registry.addConverter(getIdToUsersConverter());
        registry.addConverter(getStringToUsersConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
