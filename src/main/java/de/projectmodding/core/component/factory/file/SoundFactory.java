package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.constant.mod.ModFileNameConstants;
import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.files.SoundModel;

public class SoundFactory implements IFactory<SoundModel> {



    @Override
    public SoundModel create(ModDefinitionModel modDefinitionModel, String relatedPath) {
        return SoundModel.builder()
                .fileName(ModFileNameConstants.DEFAULT_SOUND_NAME)
                .relatedPath(relatedPath)
                .build();
    }
}
