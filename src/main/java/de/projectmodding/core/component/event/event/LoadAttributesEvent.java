package de.projectmodding.core.component.event.event;
import de.projectmodding.core.model.event.LoadAttributesEventModel;

public class LoadAttributesEvent extends AbstractEvent<LoadAttributesEventModel> {
    public LoadAttributesEvent(LoadAttributesEventModel dataModel) {
        super(dataModel);
    }
}
