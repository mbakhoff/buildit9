package esi.buildit9.rest.util;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;

public class MethodLookupHelper {

    private final Class clazz;

    public MethodLookupHelper(Class clazz) {
        this.clazz = clazz;
    }

    public ExtendedLink buildLink(int methodId, Object ... params) {
        Method method = find(clazz, methodId);
        String href = ControllerLinkBuilder.linkTo(method, params).toUri().toString();
        String rel = method.getName();
        return new ExtendedLink(href, rel, getHttpMethod(method));
    }

    private static String getHttpMethod(Method method) {
        RequestMapping mapping = method.getAnnotation(RequestMapping.class);
        if (mapping != null) {
            RequestMethod[] methods = mapping.method();
            if (methods.length == 0) {
                return RequestMethod.GET.toString();
            } else if (methods.length == 1) {
                return methods[0].toString();
            } else {
                // if you hit this, you'll probably need to make this class return a list of links
                // or you can create separate methods in your controller for each http method
                throw new RuntimeException("Method " + method.getName() + " supports multiple HTTP methods");
            }
        }
        throw new RuntimeException("Method " + method.getName() + " does not have a request mapping");
    }

    private static Method find(Class clazz, int id) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            MethodLookup lookup = method.getAnnotation(MethodLookup.class);
            if (lookup != null && lookup.value() == id) {
                return method;
            }
        }
        throw new RuntimeException("Class " + clazz.getName() + " does not contain method with id " + id);
    }

}
