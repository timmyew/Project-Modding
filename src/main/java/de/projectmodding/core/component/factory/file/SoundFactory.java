package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.files.SoundModel;

public class SoundFactory implements IFactory<SoundModel> {

    private final String DEFAULT_SOUND_NAME = "New_Sound.txt";

    @Override
    public SoundModel create(ModDefinitionModel modDefinitionModel, String relatedPath) {
        return SoundModel.builder()
                .fileName(DEFAULT_SOUND_NAME)
                .relatedPath(relatedPath)
                .build();
    }
}
