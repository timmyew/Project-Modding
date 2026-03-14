package de.projectmodding.core.component.event;

import de.projectmodding.core.component.event.system.EventSystem;

public interface Listener {
    <T> void onEvent(Event<T> event);
}
