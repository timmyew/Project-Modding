package de.projectmodding.core.component.factory;

import de.projectmodding.core.component.factory.file.ModInfoFactory;
import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.ModData;

import java.util.ArrayList;

public final class ModDataFactory {
    private ModDataFactory() {
    }

    public static ModData createDefault(ModDefinitionModel definitionModel, String rootPath) {
        return ModData.builder()
                .modInfoFile(ModInfoFactory.createSimple(definitionModel, rootPath))
                .animationFiles(new ArrayList<>())
                .model_X_files(new ArrayList<>())
                .sandboxFiles(new ArrayList<>())
                .scriptFiles(new ArrayList<>())
                .luaFiles(new ArrayList<>())
                .soundFiles(new ArrayList<>())
                .textureFiles(new ArrayList<>())
                .translationFiles(new ArrayList<>())
                .poster(null)
                .icon(null)
                .build();
    }
}
