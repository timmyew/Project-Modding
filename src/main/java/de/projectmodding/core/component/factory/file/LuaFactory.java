package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.constant.mod.ModFileNameConstants;
import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.files.LuaModel;

public class LuaFactory implements IFactory<LuaModel> {



    @Override
    public LuaModel create(ModDefinitionModel modDefinitionModel, String relatedPath) {
        return LuaModel.builder()
                .fileName(ModFileNameConstants.DEFAULT_LUA_NAME)
                .relatedPath(relatedPath)
                .build();
    }
}
