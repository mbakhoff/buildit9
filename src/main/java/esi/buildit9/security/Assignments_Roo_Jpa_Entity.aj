// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.security;

import esi.buildit9.security.Assignments;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Assignments_Roo_Jpa_Entity {
    
    declare @type: Assignments: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Assignments.id;
    
    @Version
    @Column(name = "version")
    private Integer Assignments.version;
    
    public Long Assignments.getId() {
        return this.id;
    }
    
    public void Assignments.setId(Long id) {
        this.id = id;
    }
    
    public Integer Assignments.getVersion() {
        return this.version;
    }
    
    public void Assignments.setVersion(Integer version) {
        this.version = version;
    }
    
}
