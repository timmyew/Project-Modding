package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.files.TranslationModel;

public class TranslationFactory implements IFactory<TranslationModel> {

    private final String DEFAULT_TRANSLATION_NAME = "New_Translation.txt";

    @Override
    public TranslationModel create(ModDefinitionModel modDefinitionModel, String relatedPath) {
        return TranslationModel.builder()
                .fileName(DEFAULT_TRANSLATION_NAME)
                .relatedPath(relatedPath)
                .build();
    }
}
