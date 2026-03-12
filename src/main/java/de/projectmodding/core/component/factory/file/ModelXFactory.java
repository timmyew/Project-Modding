package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.files.Model_X_Model;

public class ModelXFactory implements IFactory<Model_X_Model> {

    private final String DEFAULT_MODEL_X_NAME = "New_Model.txt";

    @Override
    public Model_X_Model create(ModDefinitionModel modDefinitionModel, String relatedPath) {
        return Model_X_Model.builder()
                .fileName(DEFAULT_MODEL_X_NAME)
                .relatedPath(relatedPath)
                .build();
    }
}
