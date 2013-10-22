package esi.buildit9.rest.util;

import org.springframework.hateoas.Link;

public class ExtendedLink extends Link {
    private static final long serialVersionUID = -9037755944661782122L;
    private String method;

    protected ExtendedLink() {
    }

    public ExtendedLink(String href, String rel, String method) {
        super(href, rel);
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
