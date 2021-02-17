package net.cg360.nsapi.commons.event;

import net.cg360.nsapi.commons.event.filter.EventFilter;

import java.util.ArrayList;
import java.util.Arrays;

public class EventManager {

    //TODO
    // All events should be handled like this note:
    // In EventManager, the events should be added to a list where they are insertion sorted
    // into order of LOWEST priority to HIGHEST.
    // Any ignoreIfCancelled's should just be skipped.
    // Vanilla events should all stem from vanilla listener methods with the HIGH priotity to give them
    // control without overriding calls made by HIGHEST listeners.
    // This behaviour may be changed in the future to properly map NSAPI event priorities to
    // vanilla priorities.

    protected ArrayList<EventFilter> filters; // Filter for EVERY listener.
    protected ArrayList<Listener> listeners;
    protected ArrayList<EventManager> children; // Send events to children too. Only sent if filter is passed.

    public EventManager(EventFilter... filters) {
        this.filters = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.children = new ArrayList<>();

        this.filters.addAll(Arrays.asList(filters));
    }


    /**
     * Registers a listener to this EventManager
     * @param listener the listener to be registered.
     */
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    /**
     * Removes listener from this EventManager and any child EventManager's
     * @param listener the listener to be removed.
     */
    public void removeListener(Listener listener) {
        removeListener(listener, true);
    }

    /**
     * Removes a listener from the managers listener list.
     * @param listener the listener to be removed.
     * @param removeFromChildren should instances of this listener be removed in child EventManager's ?
     */
    public void removeListener(Listener listener, boolean removeFromChildren) {
        listeners.remove(listener);

        if(removeFromChildren) {

            for (EventManager child : children) {
                child.removeListener(listener, true); // Ensure children don't include it either.
            }
        }
    }




    public EventFilter[] getFilters() { return filters.toArray(new EventFilter[0]); }
    public Listener[] getListeners() { return listeners.toArray(new Listener[0]); }
    public EventManager[] getChildren() { return children.toArray(new EventManager[0]); }
}
