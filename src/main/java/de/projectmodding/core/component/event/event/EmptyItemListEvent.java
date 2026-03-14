package de.projectmodding.core.component.event.event;

public class EmptyItemListEvent extends AbstractEvent<Void> {
    public EmptyItemListEvent() {
        super(null);
    }

    @Override
    public Void getData() {
        return null;
    }
}
