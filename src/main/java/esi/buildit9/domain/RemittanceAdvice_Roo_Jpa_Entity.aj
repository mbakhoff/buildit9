// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.domain;

import esi.buildit9.domain.RemittanceAdvice;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect RemittanceAdvice_Roo_Jpa_Entity {
    
    declare @type: RemittanceAdvice: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long RemittanceAdvice.id;
    
    @Version
    @Column(name = "version")
    private Integer RemittanceAdvice.version;
    
    public Long RemittanceAdvice.getId() {
        return this.id;
    }
    
    public void RemittanceAdvice.setId(Long id) {
        this.id = id;
    }
    
    public Integer RemittanceAdvice.getVersion() {
        return this.version;
    }
    
    public void RemittanceAdvice.setVersion(Integer version) {
        this.version = version;
    }
    
}
