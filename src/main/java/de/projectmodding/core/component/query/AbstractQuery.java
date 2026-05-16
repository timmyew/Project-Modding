package de.projectmodding.core.component.query;

public class AbstractQuery <T> {
    protected T model;

    protected AbstractQuery(T model) {
        this.model = model;
    }

    public T getValue(){
        return this.model;
    }
}
