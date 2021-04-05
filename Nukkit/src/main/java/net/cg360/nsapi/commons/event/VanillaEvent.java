package net.cg360.nsapi.commons.event;

import net.cg360.nsapi.commons.Check;

public final class VanillaEvent<E extends cn.nukkit.event.Event> extends BaseEvent {

    private final E wrappedEvent;

    public VanillaEvent(E event) {
        Check.nullParam(event, "event");
        this.wrappedEvent = event;
    }

    /**
     * @return the vanilla minecraft event wrapped by this custom event.
     * This makes the new Event API compatible with the vanilla APIs.
     */
    public E getWrappedEvent() {
        return wrappedEvent;
    }
}
