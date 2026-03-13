package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.constant.mod.ModFileNameConstants;
import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.files.Model_X_Model;

public class ModelXFactory implements IFactory<Model_X_Model> {



    @Override
    public Model_X_Model create(ModDefinitionModel modDefinitionModel, String relatedPath) {
        return Model_X_Model.builder()
                .fileName(ModFileNameConstants.DEFAULT_MODEL_X_NAME)
                .relatedPath(relatedPath)
                .build();
    }
}
