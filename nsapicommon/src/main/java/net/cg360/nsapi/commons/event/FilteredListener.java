package net.cg360.nsapi.commons.event;

import net.cg360.nsapi.commons.event.filter.EventFilter;
import net.cg360.nsapi.commons.event.handler.HandlerMethodPair;
import net.cg360.nsapi.commons.event.handler.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FilteredListener extends Listener {

    private List<EventFilter> filters;

    public FilteredListener(EventFilter... eventFilters) {
        super();
        this.filters = new ArrayList<>();

        this.filters.addAll(Arrays.asList(eventFilters));
    }

    @Override
    public HashMap<Priority, List<HandlerMethodPair>> getEventMethods(Event event) {
        HashMap<Priority, List<HandlerMethodPair>> methods = super.getEventMethods(event);
        // Pass to filters. They can edit the map.
        return methods;
    }
}
