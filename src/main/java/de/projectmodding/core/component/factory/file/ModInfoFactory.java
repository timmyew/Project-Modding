package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.constant.mod.ModFileNameConstants;
import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.files.ModInfoModel;

import java.io.File;
import java.util.List;

public final class ModInfoFactory {
    private ModInfoFactory() {}

    public static ModInfoModel createSimple(ModDefinitionModel modDefinitionModel, String rootPath) {
        return ModInfoModel.builder()
                .fileName(ModFileNameConstants.MOD_INFO)
                .relatedPath(rootPath.concat(File.separator).concat(
                    ""
                ))
                .attributes(List.of())
                .build();
    }
}
