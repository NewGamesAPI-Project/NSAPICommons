package net.cg360.nsapi.commons.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.cg360.nsapi.commons.Utility;
import net.cg360.nsapi.commons.exception.MissingPropertyException;
import net.cg360.nsapi.commons.math.PosRot;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Store's the core data of a point entity.
 * @author CG360
 */
public class PointEntityDataStore {

    protected String identifier;
    protected String type;

    protected PosRot pos;

    protected Map<String, String> strings;
    protected Map<String, Number> numbers;
    protected Map<String, Boolean> switches;

    public PointEntityDataStore(String identifier, String type, PosRot pos, Map<String, String> strings, Map<String, Number> numbers, Map<String, Boolean> switches) {
        this.identifier = identifier == null ? "generated-"+ Utility.generateUniqueToken(5, 3).toLowerCase() : identifier.trim().toLowerCase();
        this.type = type == null ? "static" : type.trim().toLowerCase();
        this.pos = pos;
        this.strings = strings;
        this.numbers = numbers;
        this.switches = switches;
    }


    public String getIdentifier() { return identifier; }
    public String getType() { return type; }
    public PosRot getPos() { return pos; }
    public Map<String, String> getStrings() { return strings; }
    public Map<String, Number> getNumbers() { return numbers; }
    public Map<String, Boolean> getSwitches() { return switches; }

    public static Builder builder(){ return new Builder(); }
    public static Builder builder(String identifier, JsonObject body){ return new Builder(identifier, body); }
    public static PointEntityDataStore buildFromJson(String identifier, JsonObject body) {
        return new Builder(identifier, body).build();
    }


    protected static class AssembledPointEntityDataStore extends PointEntityDataStore {

        public AssembledPointEntityDataStore(String identifier, String type, PosRot pos, Map<String, String> strings, Map<String, Number> numbers, Map<String, Boolean> switches) {
            super(identifier, type, pos, strings, numbers, switches);
        }

    }



    public static class Builder extends PointEntityDataStore {

        protected Builder() {
            super(
                    null,null,
                    new PosRot(0, 0, 0, 0, 0, false),
                    new HashMap<>(), new HashMap<>(), new HashMap<>()
            );
        }

        protected Builder(String id, JsonObject root) {
            this();
            if(root == null) throw new IllegalArgumentException("Missing PointEntity root json in Builder");
            this.setIdentifier(id);

            JsonElement typeElement = root.get("type"); //Should be string
            JsonElement posElement = root.get("pos");
            JsonElement propertiesElement = root.get("properties");

            if(typeElement instanceof JsonPrimitive){
                this.setType(((JsonPrimitive) typeElement).getAsString());
            }

            if(posElement instanceof JsonObject){
                JsonObject obj = (JsonObject) posElement;
                PosRot posRot = PosRot.parseFromJson(obj);
                this.setPos(posRot);
            } else {
                throw new MissingPropertyException("A PointEntity needs a position");
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

        public PointEntityDataStore build() {
            Map<String, String> stringMap = Collections.unmodifiableMap(strings);
            Map<String, Number> numberMap = Collections.unmodifiableMap(numbers);
            Map<String, Boolean> switchMap = Collections.unmodifiableMap(switches);
            return new AssembledPointEntityDataStore(identifier, type, pos, stringMap, numberMap, switchMap);
        }

        public Builder setIdentifier(String identifier) { this.identifier = identifier; return this; }
        public Builder setType(String type) { this.type = type; return this; }

        public Builder setPos(int x, int y, int z) { return setPos(new PosRot(x, y, z, 0, 0, false)); }
        public Builder setPos(PosRot pos){
            this.pos = pos;
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
