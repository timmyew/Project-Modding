package de.projectmodding.core.component.event.system;

import de.projectmodding.core.component.event.Event;
import de.projectmodding.core.component.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class EventSystem {
    private static EventSystem instance;
    private final HashMap<Class<? extends Event<?>> , List<Listener>> eventMap = new HashMap<>();

    private EventSystem() {
    }
    public static EventSystem getInstance() {
        if (instance == null) {
            instance = new EventSystem();
        }
        return instance;
    }

    public <T> void registerEvent(Class<? extends Event<?>> eventKey, Listener listener) {
        if (!eventMap.containsKey(eventKey)) {
            ArrayList<Listener> listeners = new ArrayList<>();
            listeners.add(listener);
            eventMap.put(eventKey, listeners);
        }
        else
            eventMap.get(eventKey).add(listener);
    }

    public void unregisterEvent(Class<? extends Event<?>> eventKey){
        eventMap.remove(eventKey);
    }

    public void fireEvent(Event<?> event){
        if (eventMap.containsKey(event.getClass())) {
            eventMap.get(event.getClass()).forEach(listener -> {
               listener.onEvent(event);
            });
        }
    }
}
