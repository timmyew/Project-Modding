package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.files.SandboxModel;

public class SandboxFactory implements IFactory<SandboxModel> {

    private final String DEFAULT_SANDBOX_NAME = "New_Sandbox.txt";

    @Override
    public SandboxModel create(ModDefinitionModel modDefinitionModel, String relatedPath) {
        return SandboxModel.builder()
                .fileName(DEFAULT_SANDBOX_NAME)
                .relatedPath(relatedPath)
                .build();
    }
}
