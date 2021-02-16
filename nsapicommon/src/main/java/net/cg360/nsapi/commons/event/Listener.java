package net.cg360.nsapi.commons.event;

import net.cg360.nsapi.commons.event.handler.EventHandler;
import net.cg360.nsapi.commons.event.handler.HandlerMethodPair;
import net.cg360.nsapi.commons.event.handler.Priority;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Listener {

    // Event -> Priority -> Method+Annotation list
    private HashMap<Class<? extends Event>, HashMap<Priority, List<HandlerMethodPair>>> listenerMethods;

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

                    checkAndAdd(type, eventClasses);
                }
            }
        }
        // manager.addListener() protected method.
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

    public HashMap<Priority, List<HandlerMethodPair>> getEventMethods(Event event) {

    }

}
