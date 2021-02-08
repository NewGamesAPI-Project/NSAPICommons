package net.cg360.nsapi;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Immutable {

    public static <K, V> Map<K, V> uMap(Map<K, V> obj, boolean isUnmodifiable){
        return isUnmodifiable ? Collections.unmodifiableMap(obj) : obj;
    }

    public static <V> List<V> uList(List<V> obj, boolean isUnmodifiable){
        return isUnmodifiable ? Collections.unmodifiableList(obj) : obj;
    }

}
