package de.projectmodding.core.model.event;

import de.projectmodding.core.component.event.event.AbstractEvent;

import java.util.HashMap;

public class StateChangeEvent extends AbstractEvent<HashMap<String, String>> {
    public StateChangeEvent(HashMap<String, String> dataModel) {
        super(dataModel);
    }
}
