package net.cg360.nsapi.commons.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.cg360.nsapi.commons.Utility;
import net.cg360.nsapi.commons.math.PosRot;
import net.cg360.nsapi.commons.math.Region;

import java.util.Collections;
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
    protected Map<String, Number> numbers;
    protected Map<String, Boolean> switches;

    public MapRegionDataStore(String identifier, String type, PosRot one, PosRot two, Map<String, String> strings, Map<String, Number> numbers, Map<String, Boolean> switches) {
        super(one, two);
        this.identifier = identifier == null ? "generated-"+ Utility.generateUniqueToken(5, 3) : identifier.trim().toLowerCase();
        this.type = type == null ? "static" : type.trim().toLowerCase();
        this.strings = strings;
        this.numbers = numbers;
        this.switches = switches;
    }


    public String getIdentifier() { return identifier; }
    public String getType() { return type; }
    public Map<String, String> getStrings() { return strings; }
    public Map<String, Number> getNumbers() { return numbers; }
    public Map<String, Boolean> getSwitches() { return switches; }

    public static Builder builder(){ return new Builder(); }
    public static Builder builder(String identifier, JsonObject body){ return new Builder(identifier, body); }
    public static MapRegionDataStore buildFromJson(String identifier, JsonObject body) {
        return new Builder(identifier, body).build();
    }



    public static class AssembledMapRegionDataStore extends MapRegionDataStore {

        public AssembledMapRegionDataStore(String identifier, String type, PosRot one, PosRot two, Map<String, String> strings, Map<String, Number> numbers, Map<String, Boolean> switches) {
            super(identifier, type, one, two, strings, numbers, switches);
        }

    }



    public static class Builder extends MapRegionDataStore {

        protected Builder() {
            super(
                    null,null,
                    new PosRot(0, 0, 0, 0, 0, false),
                    new PosRot(0, 0, 0, 0, 0, false),
                    new HashMap<>(), new HashMap<>(), new HashMap<>()
            );
        }

        protected Builder(String id, JsonObject body) {
            this();

        }

        public MapRegionDataStore build() {
            Map<String, String> stringMap = Collections.unmodifiableMap(strings);
            Map<String, Integer> integerMap = Collections.unmodifiableMap(integers);
            Map<String, Float> floatMap = Collections.unmodifiableMap(floats);
            Map<String, Boolean> switchMap = Collections.unmodifiableMap(switches);
            return new AssembledMapRegionDataStore(identifier, type, pointMin, pointMax, stringMap, integerMap, floatMap, switchMap);
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

        public Builder setNumbers(Map<String, Number> floats) {
            this.numbers = new HashMap<>();

            for(Map.Entry<String, Number> e: floats.entrySet()){
                this.numbers.put(e.getKey().trim().toLowerCase(), e.getValue());
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

        public Builder setNumber(String entry, Float value){
            this.numbers.put(entry.trim().toLowerCase(), value);
            return this;
        }

        public Builder setSwitch(String entry, Boolean value){
            this.switches.put(entry.trim().toLowerCase(), value);
            return this;
        }

    }
}
