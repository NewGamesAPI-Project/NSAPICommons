package net.cg360.nsapi.commons.event.filter;

import net.cg360.nsapi.commons.event.Event;

public interface EventFilter {

    boolean checkEvent(Event eventIn);

}
