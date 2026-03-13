package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.constant.mod.ModFileNameConstants;
import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.files.AnimationModel;

public class AnimationFactory implements IFactory<AnimationModel> {



    @Override
    public AnimationModel create(ModDefinitionModel modDefinitionModel, String relatedPath) {
        return AnimationModel.builder()
                .fileName(ModFileNameConstants.DEFAULT_ANIMATION_NAME)
                .relatedPath(relatedPath)
                .build();
    }
}
