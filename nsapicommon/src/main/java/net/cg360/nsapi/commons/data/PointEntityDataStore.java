package net.cg360.nsapi.commons.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.cg360.nsapi.Immutable;
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
        this(identifier, true, type, pos, strings, numbers, switches);
    }

    protected PointEntityDataStore(String identifier, boolean u, String type, PosRot pos, Map<String, String> strings, Map<String, Number> numbers, Map<String, Boolean> switches) {
        this.identifier = identifier == null ? "generated-"+ Utility.generateUniqueToken(5, 3).toLowerCase() : identifier.trim().toLowerCase();
        this.type = type == null ? "static" : type.trim().toLowerCase();
        this.pos = pos == null ? new PosRot(0, 0, 0, 0, 0, false) : pos;
        this.strings = Immutable.uMap(strings == null ? new HashMap<>() : strings, u);
        this.numbers = Immutable.uMap(numbers == null ? new HashMap<>() : numbers, u);
        this.switches = Immutable.uMap(switches == null ? new HashMap<>() : switches, u);
    }


    public String getIdentifier() { return identifier; }
    public String getType() { return type; }
    public PosRot getPos() { return pos; }
    public Map<String, String> getStrings() { return strings; }
    public Map<String, Number> getNumbers() { return numbers; }
    public Map<String, Boolean> getSwitches() { return switches; }

    public static Builder builder(){ return new Builder(); }

    public static PointEntityDataStore buildFromJson(String identifier, JsonObject root) {
        Builder builder = builder();

        if(root == null) throw new IllegalArgumentException("Missing PointEntity root json. This is required to build from json.");

        builder.setIdentifier(identifier);
        JsonElement typeElement = root.get("type"); //Should be string
        JsonElement posElement = root.get("pos");
        JsonElement propertiesElement = root.get("properties");

        if(typeElement instanceof JsonPrimitive){
            builder.setType(((JsonPrimitive) typeElement).getAsString());
        }

        if(posElement instanceof JsonObject){
            JsonObject obj = (JsonObject) posElement;
            PosRot posRot = PosRot.parseFromJson(obj);
            builder.setPos(posRot);

        } else {
            throw new MissingPropertyException("A PointEntity needs a position");
        }

        if(propertiesElement instanceof JsonObject){
            JsonObject map = (JsonObject) propertiesElement;

            for(Map.Entry<String, JsonElement> e: map.entrySet()){

                if(e.getValue() instanceof JsonPrimitive){
                    JsonPrimitive v = (JsonPrimitive) e.getValue();

                    if(v.isBoolean()){
                        builder.setSwitch(e.getKey(), v.getAsBoolean());

                    } else if (v.isNumber()) {
                        builder.setNumber(e.getKey(), v.getAsNumber());

                    } else {
                        builder.setString(e.getKey(), v.getAsString());
                    }
                }
            }
        }
        return builder;
    }


    protected static class AssembledPointEntityDataStore extends PointEntityDataStore {

        public AssembledPointEntityDataStore(String identifier, String type, PosRot pos, Map<String, String> strings, Map<String, Number> numbers, Map<String, Boolean> switches) {
            super(identifier, true, type, pos, strings, numbers, switches);
        }

    }



    public static class Builder extends PointEntityDataStore {

        protected Builder() {
            super(
                    null, false,null,
                    new PosRot(0, 0, 0, 0, 0, false),
                    null, null, null
            );
        }

        public PointEntityDataStore build() {
            return new AssembledPointEntityDataStore(this.identifier, this.type, this.pos, this.strings, this.numbers, this.switches);
        }

        public Builder setIdentifier(String identifier) { this.identifier = identifier; return this; }
        public Builder setType(String type) { this.type = type; return this; }

        public Builder setPos(int x, int y, int z) { return setPos(new PosRot(x, y, z, 0, 0, false)); }
        public Builder setPos(PosRot pos){
            this.pos = this.pos == null ? new PosRot(0, 0, 0, 0, 0, false) : pos;
            return this;
        }

        public Builder setStrings(Map<String, String> strings) {
            this.strings = new HashMap<>();

            if(strings != null) {

                for (Map.Entry<String, String> e : strings.entrySet()) {
                    this.strings.put(e.getKey().trim().toLowerCase(), e.getValue());
                }
            }
            return this;
        }

        public Builder setNumbers(Map<String, Number> numbers) {
            this.numbers = new HashMap<>();

            if(numbers != null) {

                for (Map.Entry<String, Number> e : numbers.entrySet()) {
                    this.numbers.put(e.getKey().trim().toLowerCase(), e.getValue());
                }
            }
            return this;
        }

        public Builder setSwitches(Map<String, Boolean> switches) {
            this.switches = new HashMap<>();

            if(switches != null) {

                for (Map.Entry<String, Boolean> e : switches.entrySet()) {
                    this.switches.put(e.getKey().trim().toLowerCase(), e.getValue());
                }
            }
            return this;
        }

        public Builder setString(String entry, String value){

            if(entry != null) {

                if (value == null) {
                    this.strings.remove(entry.trim().toLowerCase());

                } else {
                    this.strings.put(entry.trim().toLowerCase(), value);
                }
            }
            return this;
        }

        public Builder setNumber(String entry, Number value){

            if(entry != null) {

                if (value == null) {
                    this.numbers.remove(entry.trim().toLowerCase());

                } else {
                    this.numbers.put(entry.trim().toLowerCase(), value);
                }
            }
            return this;
        }

        public Builder setSwitch(String entry, Boolean value){

            if(entry != null) {

                if (value == null) {
                    this.switches.remove(entry.trim().toLowerCase());

                } else {
                    this.switches.put(entry.trim().toLowerCase(), value);
                }
            }
            return this;
        }

    }

}
