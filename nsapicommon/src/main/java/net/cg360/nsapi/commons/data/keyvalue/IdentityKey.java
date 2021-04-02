package net.cg360.nsapi.commons.data.keyvalue;

import net.cg360.nsapi.commons.Check;
import net.cg360.nsapi.commons.id.Identifier;

import java.util.Objects;

public final class IdentityKey<T> {

    private Identifier key;

    public IdentityKey(Identifier key) {
        Check.nullParam(key, "key");
        this.key = key;
    }

    public Key<T> getStringKey() {
        return new Key<>(key.getID());
    }

    public Identifier get() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if(o instanceof IdentityKey) {
            IdentityKey<?> key1 = (IdentityKey<?>) o;
            return Objects.equals(key, key1.key);

        } else if (o instanceof Key) {
            return getStringKey().equals(o);

        } else return false;


    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
