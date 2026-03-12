package de.projectmodding.core.component.loader;

import de.projectmodding.core.model.AttributeModel;
import de.projectmodding.core.model.definition.DefinitionVersionMap;

import java.util.List;

public final class ModInfoLoader {
    private static ModInfoLoader instance = null;
    private ModInfoLoader(){}

    public static ModInfoLoader getInstance(){
        if (instance == null){
            instance = new ModInfoLoader();
        }
        return instance;
    }

    public List<AttributeModel> load(String path, DefinitionVersionMap definition){

        return null;
    }
}
