package esi.buildit9;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class RBAC {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_RENTIT = "ROLE_RENTIT";
    public static final String ROLE_SITE_ENGINEER = "ROLE_SITE_ENGINEER";
    public static final String ROLE_WORKS_ENGINEER = "ROLE_WORKS_ENGINEER";

    public static void assertAuthority(String ... anyOf) {
        Set<String> granted = getAuthorities();
        if (granted.contains(ROLE_ADMIN)) {
            return;
        }
        for (String authority : anyOf) {
            if (granted.contains(authority)) {
                return;
            }
        }
        throw new UnauthorizedAccessException("RBAC access denied. Operation requires one of " + Arrays.toString(anyOf));
    }

    public static Set<String> getAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Iterator<? extends GrantedAuthority> authorities = authentication.getAuthorities().iterator();
        Set<String> auth = new HashSet<String>();
        while (authorities.hasNext()) {
            auth.add(authorities.next().getAuthority());
        }
        return auth;
    }

    public static String getUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static class UnauthorizedAccessException extends RuntimeException {
        public UnauthorizedAccessException(String message) {
            super(message);
        }
    }

}
