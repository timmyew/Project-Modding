package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.files.TextureModel;

public class TextureFactory implements IFactory<TextureModel> {

    private final String DEFAULT_TEXTURE_NAME = "New_Texture.png";

    @Override
    public TextureModel create(ModDefinitionModel modDefinitionModel, String relatedPath) {
        return TextureModel.builder()
                .fileName(DEFAULT_TEXTURE_NAME)
                .relatedPath(relatedPath)
                .build();
    }
}
