package net.cg360.nsapi.commons.data;

import net.cg360.nsapi.commons.Utility;
import net.cg360.nsapi.commons.math.PosRot;
import net.cg360.nsapi.commons.math.Region;

import java.util.HashMap;
import java.util.Map;

//TODO: Implementation in a live environment should have
// the more logical components like isEnabled or other states
// held within a "Live Region" object. This object can have a
// reference to the original MapRegionDataStore.
public class MapRegionDataStore extends Region {

    protected String identifier;
    protected String type;

    protected Map<String, String> strings;
    protected Map<String, Integer> integers;
    protected Map<String, Float> floats;
    protected Map<String, Boolean> switches;

    protected MapRegionDataStore(String identifier, String type, PosRot one, PosRot two) {
        super(one, two);

        this.identifier = identifier == null ? "generated-"+ Utility.generateUniqueToken(5, 3) : identifier.trim().toLowerCase();
        this.type = type == null ? "static" : type.trim().toLowerCase();

        this.strings = new HashMap<>();
        this.integers = new HashMap<>();
        this.floats = new HashMap<>();
        this.switches = new HashMap<>();
    }

    protected void setStrings(Map<String, String> strings) { this.strings = strings; }
    protected void setIntegers(Map<String, Integer> integers) { this.integers = integers; }
    protected void setFloats(Map<String, Float> floats) { this.floats = floats; }
    protected void setSwitches(Map<String, Boolean> switches) { this.switches = switches; }
}
