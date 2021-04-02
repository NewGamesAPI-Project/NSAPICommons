package net.cg360.nsapi.commons.event;

import net.cg360.nsapi.commons.CommonLog;
import net.cg360.nsapi.commons.event.filter.EventFilter;
import net.cg360.nsapi.commons.event.handler.HandlerMethodPair;
import net.cg360.nsapi.commons.event.type.Cancellable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
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

    private static EventManager primaryManager;

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
     * Sets the manager the result provided from EventManager#get() and
     * finalizes the instance to an extent.
     *
     * Cannot be changed once initially called.
     */
    public void setAsPrimaryManager(){
        if(primaryManager == null) primaryManager = this;
    }

    public void call(Event event) {
        ArrayList<HandlerMethodPair> callList = new ArrayList<>();

        // And this is the part where it's probably the least efficient.
        // Would be great to bake this but then I can't really use the FilteredListener.
        // Could maybe filter each method as I go?
        for(Listener listener: listeners) {

            for(HandlerMethodPair pair : listener.getEventMethods(event)) {

                // Handles generics in class parameters.
                // These would be a nightmare to integrate into the listener's map.
                TypeVariable<? extends Class<?>>[] source = event.getClass().getTypeParameters();
                TypeVariable<? extends Class<?>>[] target = pair.getMethod().getParameterTypes()[0].getTypeParameters();
                if(!(source.length == target.length)) continue; // Not even the right length!

                boolean fail = false;
                for(int i = 0; i < source.length; i++) {
                    TypeVariable<? extends Class<?>> sourceType = source[i];
                    TypeVariable<? extends Class<?>> targetType = target[i];

                    if(!sourceType.getGenericDeclaration().isAssignableFrom(targetType.getGenericDeclaration())){
                        fail = true;
                        break;
                    }
                }

                if(fail) continue;

                // ^ should just be skipped if generics aren't found ^


                boolean added = false;
                int pairPriority = pair.getAnnotation().getPriority().getValue();
                int originalSize = callList.size();

                for(int i = 0; i < originalSize; i++) {
                    HandlerMethodPair p = callList.get(i);

                    if(pairPriority > p.getAnnotation().getPriority().getValue()) {
                        callList.add(i, pair);
                        added = true;
                        break;
                    }
                }

                if(!added){
                    callList.add(pair);
                }
            }
        }

        // Separating them to save a tiny bit more time on each iteration.
        if(event instanceof Cancellable) {
            Cancellable cancellable = (Cancellable) event;

            for (HandlerMethodPair methodPair : callList) {
                // Skip if cancelled and ignoring cancelled.
                if(cancellable.isCancelled() && methodPair.getAnnotation().ignoreIfCancelled()) continue;
                invokeEvent(event, methodPair);
            }

        } else {

            for (HandlerMethodPair methodPair : callList) invokeEvent(event, methodPair);
        }

    }


    /**
     * Registers a listener to this EventManager
     * @param listener the listener to be registered.
     * @return listener for storing an instance.
     */
    public Listener addListener(Listener listener) {
        removeListener(listener, true);
        // Check that it isn't duped by clearing it.
        // If someone has used the same object instance to create two objects and overrided
        // #equals(), that's their problem.
        listeners.add(listener);
        return listener;
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


    private static void invokeEvent(Event event, HandlerMethodPair methodPair) {
        try {
            methodPair.getMethod().invoke(event);

        } catch (Exception err) {
            CommonLog.get().error("EventSystem", "An error was thrown during the invocation of an event.");
            err.printStackTrace();
        }
    }



    /** @return the primary instance of the EventManager. */
    public static EventManager get(){
        return primaryManager;
    }

    public EventFilter[] getFilters() { return filters.toArray(new EventFilter[0]); }
    public Listener[] getListeners() { return listeners.toArray(new Listener[0]); }
    public EventManager[] getChildren() { return children.toArray(new EventManager[0]); }
}
