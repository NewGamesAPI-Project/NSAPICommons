package net.cg360.nsapi.commons.event;

import net.cg360.nsapi.commons.event.filter.EventFilter;
import net.cg360.nsapi.commons.event.handler.HandlerMethodPair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NSFilteredListener extends NSListener {

    private List<EventFilter> filters;

    public NSFilteredListener(Object sourceObject, EventFilter... eventFilters) {
        super(sourceObject);
        this.filters = new ArrayList<>();

        this.filters.addAll(Arrays.asList(eventFilters));
    }

    @Override
    public ArrayList<HandlerMethodPair> getEventMethods(BaseEvent event) {
        ArrayList<HandlerMethodPair> methods = super.getEventMethods(event);
        // Pass to filters. They can edit the map.
        return methods;
    }

    public List<EventFilter> getFilters() {
        return new ArrayList<>(filters);
    }
}
