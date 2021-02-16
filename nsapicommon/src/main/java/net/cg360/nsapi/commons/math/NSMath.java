package net.cg360.nsapi.commons.math;

/**
 * A utility class dedicated to any method that works
 * with numbers.
 */
public class NSMath {

    public static byte getBit(byte byteIn, byte position) {
        return (byte) ((byteIn >> position) & 0x01);
    }

    public static byte setBitOne(byte byteIn, byte position) {
        return (byte) (byteIn | 1 << position);
    }

    public static byte setBitZero(byte byteIn, byte position) {
        return (byte) (byteIn & ~(1 << position));
    }

}
