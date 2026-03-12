package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.files.AnimationModel;

public class AnimationFactory implements IFactory<AnimationModel> {

    private final String DEFAULT_ANIMATION_NAME = "New_Animation.txt";

    @Override
    public AnimationModel create(ModDefinitionModel modDefinitionModel, String relatedPath) {
        return AnimationModel.builder()
                .fileName(DEFAULT_ANIMATION_NAME)
                .relatedPath(relatedPath)
                .build();
    }
}
