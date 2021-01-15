package net.cg360.nsapi.commons;

import java.util.regex.Pattern;

/**
 * A few utility methods.
 * @author CG360
 */
public class Utility {

    public static String createNamespacedID(String identifier){
        if(!identifier.contains(":")){
            return CommonConstants.COMMON_PREFIX + ":" + identifier;
        } else {
            return identifier;
        }
    }

}
