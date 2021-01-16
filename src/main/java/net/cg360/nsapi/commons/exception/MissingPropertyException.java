package net.cg360.nsapi.commons.exception;

/**
 * For use when a property is missing from a MapID
 * @author CG360
 */
public class MissingPropertyException extends RuntimeException {

    public MissingPropertyException() { super(); }
    public MissingPropertyException(String str) { super(str); }

}
