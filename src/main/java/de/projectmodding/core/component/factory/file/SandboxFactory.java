package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.constant.mod.ModFileNameConstants;
import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.files.SandboxModel;

public class SandboxFactory implements IFactory<SandboxModel> {



    @Override
    public SandboxModel create(ModDefinitionModel modDefinitionModel, String relatedPath) {
        return SandboxModel.builder()
                .fileName(ModFileNameConstants.DEFAULT_SANDBOX_NAME)
                .relatedPath(relatedPath)
                .build();
    }
}

