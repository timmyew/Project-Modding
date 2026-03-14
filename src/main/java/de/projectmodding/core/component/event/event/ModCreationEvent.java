package de.projectmodding.core.component.event.event;

import de.projectmodding.core.component.event.Event;
import de.projectmodding.core.model.mod.ModPackageModel;

public class ModCreationEvent extends AbstractEvent<ModPackageModel> {
    public ModCreationEvent(ModPackageModel modPackageModel) {
        super(modPackageModel);
    }
}
