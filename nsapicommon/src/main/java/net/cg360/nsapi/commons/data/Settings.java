package net.cg360.nsapi.commons.data;

import net.cg360.nsapi.commons.Immutable;
import net.cg360.nsapi.commons.data.keyvalue.Key;
import net.cg360.nsapi.commons.data.keyvalue.Value;

import java.util.HashMap;
import java.util.Map;

//TODO: Make all get...() methods search the other lists
// for potentially valid properties.

//TODO: Replace Settings with a Key driven data access

/**
 * A way of storing settings/properties with
 * the option to lock them.
 *
 * Uses String keys rather than Identifier keys.
 */
public final class Settings {

    private Map<Key<?>, Value<?>> dataMap;
    private boolean isLocked;


    public Settings () {
        this.dataMap = new HashMap<>();
        this.isLocked = false;
    }

    /** Used to duplicate a Settings instance.*/
    protected Settings(Settings duplicate) {
        this.dataMap = new HashMap<>(duplicate.dataMap);
        this.isLocked = false;
    }



    /** Prevents setting data within this Settings object. */
    public Settings lock() {
        // Duplicate maps + make them unmodifiable if unlocked still.
        if(!this.isLocked) {
            this.dataMap = Immutable.uMap(this.dataMap, true);
            this.isLocked = true;
        }
        return this;
    }


    /** Sets a key within the settings if they are unlocked. */
    public <T> Settings set(Key<T> key, Value<T> value) {
        if(!isLocked) dataMap.put(key, value);
        return this;
    }


    /** Returns a property with the same type as the key. */
    @SuppressWarnings("unchecked")
    public <T> Value<T> get(Key<T> id) {
        Value<?> v = dataMap.get(id);
        return v == null ? null : (Value<T>) v;
    }

    /** @return a copy of these settings which is unlocked. */
    public Settings getUnlockedCopy() {
        return new Settings(this);
    }

    public boolean isLocked() { return isLocked; }
}
