package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.files.LuaModel;

public class LuaFactory implements IFactory<LuaModel> {

    private final String DEFAULT_LUA_NAME = "New_Lua.lua";

    @Override
    public LuaModel create(ModDefinitionModel modDefinitionModel, String relatedPath) {
        return LuaModel.builder()
                .fileName(DEFAULT_LUA_NAME)
                .relatedPath(relatedPath)
                .build();
    }
}
