package de.projectmodding.core.service;

import de.projectmodding.core.component.loader.DefinitionLoader;
import de.projectmodding.core.model.definition.DefinitionVersionMap;

public class DefinitionService {
    private final DefinitionLoader  definitionLoader = DefinitionLoader.getInstance();

    public DefinitionService(){

    }

    public DefinitionVersionMap load(){
        return definitionLoader.load();
    }
}
