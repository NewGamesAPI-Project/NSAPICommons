package net.cg360.nsapi.commons.event;

import net.cg360.nsapi.commons.event.handler.EventHandler;
import net.cg360.nsapi.commons.event.handler.HandlerMethodPair;
import net.cg360.nsapi.commons.event.handler.Priority;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;

// This whole class probably isn't an efficient EventListener
// implementation. If anyone has any suggestions, create a PR. All
// that needs to stay intact is the FilteredListener's added filter
// behaviour
public class Listener {

    // Event Class -> Method+Annotation list
    private HashMap<Class<? extends Event>, ArrayList<HandlerMethodPair>> listenerMethods;

    public Listener () {
        this.listenerMethods = new HashMap<>();
    }

    public final void registerListener(EventManager manager) {

        for(Method method : this.getClass().getMethods()) {

            if(method.isAnnotationPresent(EventHandler.class)) {
                EventHandler annotation = method.getAnnotation(EventHandler.class);
                Parameter[] parameters = method.getParameters();

                if(parameters.length == 1){
                    Class<?> type = parameters[0].getType();
                    ArrayList<Class<? extends Event>> eventClasses = new ArrayList<>();

                    HandlerMethodPair pair = new HandlerMethodPair(annotation, method);

                    checkAndAdd(type, eventClasses); // Get all the categories this method would be in.

                    for(Class<? extends Event> cls: eventClasses) {

                        if(!listenerMethods.containsKey(cls)){
                            listenerMethods.put(cls, new ArrayList<>()); // Create new handler list if it doesn't exist.
                        }
                        listenerMethods.get(cls).add(pair);
                    }
                }
            }
        }

        // manager.addListener() protected method to add listener to manager. Should NOT be public.
    }

    @SuppressWarnings("unchecked") // It's checked :)
    private void checkAndAdd(Class<?> classIn, ArrayList<Class<? extends Event>> list) {
        if(classIn == null) return;

        if(classIn.isAssignableFrom(Event.class)){
            list.add((Class<? extends Event>) classIn);
            checkAndAdd(classIn.getSuperclass(), list);

            for(Class<?> cls: classIn.getInterfaces()) {
                checkAndAdd(cls, list);
            }
        }
    }

    public ArrayList<HandlerMethodPair> getEventMethods(Event event) {
        ArrayList<HandlerMethodPair> ls = listenerMethods.get(event.getClass());

        return ls == null ? new ArrayList<>() : new ArrayList<>(ls); // Empty list if no methods exist. Otherwise clone.
    }

}
