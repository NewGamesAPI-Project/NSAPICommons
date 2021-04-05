package net.cg360.nsapi.commons.event.handler;

import java.lang.reflect.Method;

public final class HandlerMethodPair {

    private NSEventHandler annotation;
    private Method method;

    public HandlerMethodPair(NSEventHandler annotation, Method method) {
        this.annotation = annotation;
        this.method = method;
    }

    public NSEventHandler getAnnotation() { return annotation; }
    public Method getMethod() { return method; }

}
