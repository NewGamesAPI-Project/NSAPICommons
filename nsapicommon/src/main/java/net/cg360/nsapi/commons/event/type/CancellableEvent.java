package net.cg360.nsapi.commons.event.type;

import net.cg360.nsapi.commons.event.Event;

/**
 * An implementation of the Cancellable interface applied to
 * an Event. Can be used as a base
 */
public abstract class CancellableEvent extends Event implements Cancellable {

    private boolean isCancelled = false;

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
