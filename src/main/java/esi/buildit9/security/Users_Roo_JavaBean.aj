// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package esi.buildit9.security;

import esi.buildit9.security.Users;

privileged aspect Users_Roo_JavaBean {
    
    public String Users.getUsername() {
        return this.username;
    }
    
    public void Users.setUsername(String username) {
        this.username = username;
    }
    
    public String Users.getPassword() {
        return this.password;
    }
    
    public void Users.setPassword(String password) {
        this.password = password;
    }
    
    public Boolean Users.getEnabled() {
        return this.enabled;
    }
    
    public void Users.setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
}
