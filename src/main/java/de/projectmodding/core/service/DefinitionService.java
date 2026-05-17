package de.projectmodding.core.service;

import de.projectmodding.core.component.container.Container;
import de.projectmodding.core.component.loader.DefinitionLoader;
import de.projectmodding.core.model.definition.DefinitionVersionMap;

public class DefinitionService {
    private final Container container;

    public DefinitionService(Container mainContainer) {
        this.container = mainContainer;
    }

    public DefinitionVersionMap load(){
        return container.resolve(DefinitionLoader.class).load();
    }
}
