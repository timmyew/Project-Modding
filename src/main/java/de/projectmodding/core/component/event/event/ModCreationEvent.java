package de.projectmodding.core.component.event.event;

import de.projectmodding.core.component.event.Event;
import de.projectmodding.core.model.mod.ModPackageModel;

public class ModCreationEvent implements Event<ModPackageModel> {
    private final ModPackageModel modPackageModel;
    public ModCreationEvent(ModPackageModel modPackageModel) {
        this.modPackageModel = modPackageModel;
    }

    @Override
    public ModPackageModel getData() {
        return modPackageModel;
    }
}
