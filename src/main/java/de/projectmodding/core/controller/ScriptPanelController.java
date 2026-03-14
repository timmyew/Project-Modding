package de.projectmodding.core.controller;

import de.projectmodding.core.model.mod.files.data.ScriptBlock;
import de.projectmodding.core.service.ModDataGenerationService;
import de.projectmodding.core.service.RuntimeDataService;

import java.util.HashMap;
import java.util.List;

public class ScriptPanelController implements IScriptPanelController {
    private final RuntimeDataService runtimeDataService;
    private final ModDataGenerationService modDataGenerationService;

    public ScriptPanelController(RuntimeDataService runtimeDataService, ModDataGenerationService modDataGenerationService) {
        this.runtimeDataService = runtimeDataService;
        this.modDataGenerationService = modDataGenerationService;
    }

    @Override
    public ScriptBlock createScriptBlock(String modName, String version, String fileName, String scriptBlockName) {
        return modDataGenerationService.createScriptBlock(modName, version, fileName, scriptBlockName);
    }

    @Override
    public void removeScriptBlock(String modName, String version, String fileName, String scriptBlockName) {
        modDataGenerationService.removeScriptBlock(modName, version, fileName, scriptBlockName);
    }

    @Override
    public HashMap<String, List<String>> getScriptBlockTypes(String version) {
        return modDataGenerationService.loadScriptBlockTypes(version);
    }
}
