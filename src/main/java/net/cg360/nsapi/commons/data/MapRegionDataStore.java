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
public abstract class MapRegionDataStore extends Region {

    protected String identifier;
    protected String type;

    protected Map<String, String> strings;
    protected Map<String, Integer> integers;
    protected Map<String, Float> floats;
    protected Map<String, Boolean> switches;

    public MapRegionDataStore(String identifier, String type, PosRot one, PosRot two, Map<String, String> strings, Map<String, Integer> integers, Map<String, Float> floats, Map<String, Boolean> switches) {
        super(one, two);
        this.identifier = identifier == null ? "generated-"+ Utility.generateUniqueToken(5, 3) : identifier.trim().toLowerCase();
        this.type = type == null ? "static" : type.trim().toLowerCase();
        this.strings = strings;
        this.integers = integers;
        this.floats = floats;
        this.switches = switches;
    }


    public String getIdentifier() { return identifier; }
    public String getType() { return type; }
    public Map<String, String> getStrings() { return strings; }
    public Map<String, Integer> getIntegers() { return integers; }
    public Map<String, Float> getFloats() { return floats; }
    public Map<String, Boolean> getSwitches() { return switches; }

    public static Builder builder(){ return new Builder(); }



    public static class AssembledMapRegionDataStore extends MapRegionDataStore {

        public AssembledMapRegionDataStore(String identifier, String type, PosRot one, PosRot two, Map<String, String> strings, Map<String, Integer> integers, Map<String, Float> floats, Map<String, Boolean> switches) {
            super(identifier, type, one, two, strings, integers, floats, switches);
        }

    }



    public static class Builder extends MapRegionDataStore {

        protected Builder() {
            super(
                    null,null,
                    new PosRot(0, 0, 0, 0, 0, false),
                    new PosRot(0, 0, 0, 0, 0, false),
                    new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()
            );
        }

        public Builder setIdentifier(String identifier) { this.identifier = identifier; return this; }
        public Builder setType(String type) { this.type = type; return this; }

        public Builder setPosMin(int x, int y, int z) { return setPosMin(new PosRot(x, y, z, 0, 0, false)); }
        public Builder setPosMin(PosRot posRot){
            this.pointMin = posRot;
            this.orderCorners();
            return this;
        }

        public Builder setPosMax(int x, int y, int z) { return setPosMax(new PosRot(x, y, z, 0, 0, false)); }
        public Builder setPosMax(PosRot posRot){
            this.pointMax = posRot;
            this.orderCorners();
            return this;
        }

        public Builder setStrings(Map<String, String> strings) {
            this.strings = new HashMap<>();

            for(Map.Entry<String, String> e: strings.entrySet()){
                this.strings.put(e.getKey().trim().toLowerCase(), e.getValue());
            }
            return this;
        }

        public Builder setIntegers(Map<String, Integer> integers) {
            this.integers = new HashMap<>();

            for(Map.Entry<String, Integer> e: integers.entrySet()){
                this.integers.put(e.getKey().trim().toLowerCase(), e.getValue());
            }
            return this;
        }

        public Builder setFloats(Map<String, Float> floats) {
            this.floats = new HashMap<>();

            for(Map.Entry<String, Float> e: floats.entrySet()){
                this.floats.put(e.getKey().trim().toLowerCase(), e.getValue());
            }
            return this;
        }

        public Builder setSwitches(Map<String, Boolean> switches) {
            this.switches = new HashMap<>();

            for(Map.Entry<String, Boolean> e: switches.entrySet()){
                this.switches.put(e.getKey().trim().toLowerCase(), e.getValue());
            }
            return this;
        }

        public Builder setString(String entry, String value){
            this.strings.put(entry.trim().toLowerCase(), value);
            return this;
        }

        public Builder setInteger(String entry, Integer value){
            this.integers.put(entry.trim().toLowerCase(), value);
            return this;
        }

        public Builder setFloat(String entry, Float value){
            this.floats.put(entry.trim().toLowerCase(), value);
            return this;
        }

        public Builder setSwitch(String entry, Boolean value){
            this.switches.put(entry.trim().toLowerCase(), value);
            return this;
        }

    }
}
