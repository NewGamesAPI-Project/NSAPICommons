package net.cg360.nsapi.commons;

import net.cg360.nsapi.commons.exception.MissingPropertyException;

public final class Check {

    public static void missingProperty(Object obj, String loc, String name) {
        if(isNonNull(obj)) throw new MissingPropertyException(String.format("%s is missing a valid '%s' property.", loc, name));
    }

    public static void nullParam(Object obj, String loc, String name) {
        if(isNonNull(obj)) throw new IllegalArgumentException(String.format("'%s' in %s cannot be null.", loc, name));
    }

    public static boolean isNonNull(Object obj) {
        return obj != null;
    }

}
