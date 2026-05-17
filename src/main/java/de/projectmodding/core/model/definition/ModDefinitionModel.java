package de.projectmodding.core.model.definition;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ModDefinitionModel {
    FileDefinitionModel modInfo;
    FileDefinitionModel script;
    FileDefinitionModel modFolder;
    FileDefinitionModel workShop;

    public static ModDefinitionModel copy(ModDefinitionModel model) {
        return ModDefinitionModel.builder()
                .modInfo(FileDefinitionModel.copy(model.getModInfo()))
                .script(FileDefinitionModel.copy(model.getScript()))
                .modFolder(FileDefinitionModel.copy(model.getModFolder()))
                .workShop(FileDefinitionModel.copy(model.getWorkShop()))
                .build();

    }
}
