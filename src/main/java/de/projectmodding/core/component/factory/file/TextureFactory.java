package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.constant.mod.ModFileNameConstants;
import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.files.TextureModel;

public class TextureFactory implements IFactory<TextureModel> {



    @Override
    public TextureModel create(ModDefinitionModel modDefinitionModel, String relatedPath) {
        return TextureModel.builder()
                .fileName(ModFileNameConstants.DEFAULT_TEXTURE_NAME)
                .relatedPath(relatedPath)
                .build();
    }
}
