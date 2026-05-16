package de.projectmodding.core.model.event;

import de.projectmodding.core.component.event.event.AbstractEvent;
import de.projectmodding.core.model.intern.Pair;

import java.util.HashMap;

public class StateChangeEvent extends AbstractEvent<Pair<String, String>> {
    public StateChangeEvent(Pair<String, String> dataModel) {
        super(dataModel);
    }
}
