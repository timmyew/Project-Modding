package de.projectmodding.core.component.event;

public interface Listener {
    <T> void onEvent(Event<T> event);
}
