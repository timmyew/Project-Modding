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
}
