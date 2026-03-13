package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.constant.mod.ModFileNameConstants;
import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.files.TranslationModel;

public class TranslationFactory implements IFactory<TranslationModel> {



    @Override
    public TranslationModel create(ModDefinitionModel modDefinitionModel, String relatedPath) {
        return TranslationModel.builder()
                .fileName(ModFileNameConstants.DEFAULT_TRANSLATION_NAME)
                .relatedPath(relatedPath)
                .build();
    }
}
