package de.projectmodding.core.controller;

import de.projectmodding.core.component.container.Container;
import de.projectmodding.core.enums.ModDataEnum;
import de.projectmodding.core.model.definition.DefinitionVersionMap;
import de.projectmodding.core.model.definition.FileDefinitionModel;
import de.projectmodding.core.model.mod.files.data.ScriptBlock;
import de.projectmodding.core.service.ModDataGenerationService;
import de.projectmodding.core.service.RuntimeDataService;

import java.util.HashMap;
import java.util.List;

public class ScriptPanelController implements IScriptPanelController {
    private final Container container;

    public ScriptPanelController(Container mainContainer) {
        this.container = mainContainer;
    }

    @Override
    public ScriptBlock createScriptBlock(String modName, String version, String fileName, String scriptBlockName) {
        return container.resolve(ModDataGenerationService.class).createScriptBlock(modName, version, fileName, scriptBlockName);
    }

    @Override
    public void removeScriptBlock(String modName, String version, String fileName, String scriptBlockName) {
        container.resolve(ModDataGenerationService.class).removeScriptBlock(modName, version, fileName, scriptBlockName);
    }

    @Override
    public HashMap<String, HashMap<String, List<String>>> getScriptBlockTypes(String version) {
        return container.resolve(ModDataGenerationService.class).loadScriptBlockTypes(version);
    }

    @Override
    public FileDefinitionModel getScriptDefinition(String version) {
        return container.resolve(ModDataGenerationService.class).getModDefinitionModel(version).getScript();
    }
}
