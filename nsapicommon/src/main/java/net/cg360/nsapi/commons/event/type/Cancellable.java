package net.cg360.nsapi.commons.event.type;

import net.cg360.nsapi.commons.event.Event;

public interface Cancellable {

    boolean isCancelled();

    default void setCancelled() { setCancelled(true); }
    void setCancelled(boolean isCancelled);

}
