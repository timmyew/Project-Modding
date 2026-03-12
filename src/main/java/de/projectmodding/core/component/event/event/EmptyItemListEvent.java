package de.projectmodding.core.component.event.event;

import de.projectmodding.core.component.event.Event;

public class EmptyItemListEvent implements Event<Void> {
    @Override
    public Void getData() {
        return null;
    }
}
