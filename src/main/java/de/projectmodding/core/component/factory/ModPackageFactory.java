package de.projectmodding.core.component.factory;

import de.projectmodding.core.component.factory.file.WorkshopFactory;
import de.projectmodding.core.model.definition.DefinitionVersionMap;
import de.projectmodding.core.model.mod.ModPackageModel;

import java.util.List;

public final class ModPackageFactory {
    private ModPackageFactory() {
    }

    public static ModPackageModel createSimple(String packageName, String rootPath, String version, String packageVersion, DefinitionVersionMap definitionVersionMap) {
        return ModPackageModel.builder()
                .packetName(packageName)
                .rootPath(
                        rootPath.concat(packageName)
                )
                .workshop(
                        WorkshopFactory.createDefaultWorkshopModel(
                                definitionVersionMap.getMap().get(version),
                                packageName,
                                packageVersion,
                                rootPath
                        )
                )
                .modList(List.of(ModFactory.createSimple(definitionVersionMap.getMap().get(version), rootPath, version)))
                .build();
    }
}
