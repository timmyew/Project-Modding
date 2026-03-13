package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.constant.mod.ModFileNameConstants;
import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.files.ScriptModel;

import java.util.ArrayList;

public class ScriptFactory implements IFactory<ScriptModel> {
    private final String DEFAULT_MODULE_NAME = "base";


    @Override
    public ScriptModel create(ModDefinitionModel modDefinitionModel, String relatedPath) {
        return ScriptModel.builder()
                .moduleName(DEFAULT_MODULE_NAME)
                .fileName(ModFileNameConstants.DEFAULT_SCRIPT_NAME)
                .items(new ArrayList<>())
                .relatedPath(relatedPath)
                .build();
    }
}
