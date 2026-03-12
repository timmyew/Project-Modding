package de.projectmodding.core.component.factory;

import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.ModData;
import de.projectmodding.core.model.mod.ModModel;

import java.util.HashMap;

public final class ModFactory {
    private static final String DEFAULT_MOD_NAME = "New Mod";
    private ModFactory(){}

    public static ModModel createSimple(ModDefinitionModel definitionModel, String rootPath, String version) {
        return ModModel.builder()
                .modName(DEFAULT_MOD_NAME)
                .common(ModDataFactory.createDefault(definitionModel,  rootPath))
                .modVersionMap(getModDataMap(definitionModel,  rootPath, version))
                .build();
    }

    private static HashMap<String, ModData> getModDataMap(ModDefinitionModel definitionModel, String rootPath,String version){
        HashMap<String, ModData> map = new HashMap<>();
        map.put(version, ModDataFactory.createDefault(definitionModel, rootPath));
        return map;
    }
}
