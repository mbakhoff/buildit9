// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.security;

import esi.buildit9.security.AssignmentsDerp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect AssignmentsDerp_Roo_Jpa_Entity {
    
    declare @type: AssignmentsDerp: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long AssignmentsDerp.id;
    
    @Version
    @Column(name = "version")
    private Integer AssignmentsDerp.version;
    
    public Long AssignmentsDerp.getId() {
        return this.id;
    }
    
    public void AssignmentsDerp.setId(Long id) {
        this.id = id;
    }
    
    public Integer AssignmentsDerp.getVersion() {
        return this.version;
    }
    
    public void AssignmentsDerp.setVersion(Integer version) {
        this.version = version;
    }
    
}