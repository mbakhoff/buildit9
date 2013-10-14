// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.domain;

import esi.buildit9.domain.Site;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Site_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Site.entityManager;
    
    public static final EntityManager Site.entityManager() {
        EntityManager em = new Site().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Site.countSites() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Site o", Long.class).getSingleResult();
    }
    
    public static List<Site> Site.findAllSites() {
        return entityManager().createQuery("SELECT o FROM Site o", Site.class).getResultList();
    }
    
    public static Site Site.findSite(Long id) {
        if (id == null) return null;
        return entityManager().find(Site.class, id);
    }
    
    public static List<Site> Site.findSiteEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Site o", Site.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Site.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Site.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Site attached = Site.findSite(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Site.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Site.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Site Site.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Site merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}