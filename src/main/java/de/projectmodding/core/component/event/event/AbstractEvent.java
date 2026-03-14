package de.projectmodding.core.component.event.event;

import de.projectmodding.core.component.event.Event;

public abstract class AbstractEvent<T> implements Event<T> {
    private final T dataModel;

    public AbstractEvent(T dataModel){
        this.dataModel = dataModel;
    }

    public T getData(){
        return this.dataModel;
    }
}
