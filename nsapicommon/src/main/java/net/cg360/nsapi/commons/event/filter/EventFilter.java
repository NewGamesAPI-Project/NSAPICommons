package net.cg360.nsapi.commons.event.filter;

import net.cg360.nsapi.commons.event.BaseEvent;

public interface EventFilter {

    boolean checkEvent(BaseEvent eventIn);

}
