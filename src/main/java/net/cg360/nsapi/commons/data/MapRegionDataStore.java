package net.cg360.nsapi.commons.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.cg360.nsapi.commons.Utility;
import net.cg360.nsapi.commons.exception.MissingPropertyException;
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

        protected Builder(String id, JsonObject root) {
            this();
            if(root == null) throw new IllegalArgumentException("Missing MapRegion root json in Builder");
            this.setIdentifier(id);

            JsonElement typeElement = root.get("type"); //Should be string
            JsonElement oneElement = root.get("1");
            JsonElement twoElement = root.get("2");
            JsonElement propertiesElement = root.get("properties");

            if(typeElement instanceof JsonPrimitive){
                this.setType(((JsonPrimitive) typeElement).getAsString());
            }

            if(oneElement instanceof JsonObject && twoElement instanceof JsonObject){
                JsonObject objOne = (JsonObject) oneElement;
                JsonObject objTwo = (JsonObject) twoElement;
                PosRot posRotOne = PosRot.parseFromJson(objOne);
                PosRot posRotTwo = PosRot.parseFromJson(objTwo);
                this.setPositions(posRotOne, posRotTwo);
            } else {
                throw new MissingPropertyException("A MapRegion needs 2 points (names 'one' and 'two')");
            }

            if(propertiesElement instanceof JsonObject){
                JsonObject map = (JsonObject) propertiesElement;

                for(Map.Entry<String, JsonElement> e: map.entrySet()){
                    if(e.getValue() instanceof JsonPrimitive){
                        JsonPrimitive v = (JsonPrimitive) e.getValue();
                        if(v.isBoolean()){
                            this.setSwitch(e.getKey(), v.getAsBoolean());
                        } else if (v.isNumber()) {
                            this.setNumber(e.getKey(), v.getAsNumber());
                        } else {
                            this.setString(e.getKey(), v.getAsString());
                        }
                    }
                }
            }
        }

        public MapRegionDataStore build() {
            Map<String, String> stringMap = Collections.unmodifiableMap(strings);
            Map<String, Number> numberMap = Collections.unmodifiableMap(numbers);
            Map<String, Boolean> switchMap = Collections.unmodifiableMap(switches);
            return new AssembledMapRegionDataStore(identifier, type, pointMin, pointMax, stringMap, numberMap, switchMap);
        }

        public Builder setIdentifier(String identifier) { this.identifier = identifier; return this; }
        public Builder setType(String type) { this.type = type; return this; }

        public Builder setPositions(int x1, int y1, int z1, int x2, int y2, int z2) {
            return setPositions(
                    new PosRot(x1, y1, z1, 0, 0, false),
                    new PosRot(x2, y2, z2, 0, 0, false)
            );
        }

        public Builder setPositions(PosRot posRotOne, PosRot posRotTwo){
            this.pointMin = posRotOne;
            this.pointMax = posRotTwo;
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

        public Builder setNumber(String entry, Number value){
            this.numbers.put(entry.trim().toLowerCase(), value);
            return this;
        }

        public Builder setSwitch(String entry, Boolean value){
            this.switches.put(entry.trim().toLowerCase(), value);
            return this;
        }

    }
}
