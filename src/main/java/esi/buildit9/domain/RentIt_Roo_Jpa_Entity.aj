// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.domain;

import esi.buildit9.domain.RentIt;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect RentIt_Roo_Jpa_Entity {
    
    declare @type: RentIt: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long RentIt.id;
    
    @Version
    @Column(name = "version")
    private Integer RentIt.version;
    
    public Long RentIt.getId() {
        return this.id;
    }
    
    public void RentIt.setId(Long id) {
        this.id = id;
    }
    
    public Integer RentIt.getVersion() {
        return this.version;
    }
    
    public void RentIt.setVersion(Integer version) {
        this.version = version;
    }
    
}