package de.projectmodding.core.controller;

import de.projectmodding.core.model.mod.files.data.ScriptBlock;
import de.projectmodding.core.service.ModGenerationService;
import de.projectmodding.core.service.RuntimeDataService;

public class ScriptPanelController implements IScriptPanelController {
    private final RuntimeDataService runtimeDataService;
    private final ModGenerationService modGenerationService;

    public ScriptPanelController(RuntimeDataService runtimeDataService, ModGenerationService modGenerationService) {
        this.runtimeDataService = runtimeDataService;
        this.modGenerationService = modGenerationService;
    }

    @Override
    public ScriptBlock createScriptBlock(String modName, String version, String fileName, String scriptBlockName) {
        return modGenerationService.createScriptBlock(modName, version, fileName, scriptBlockName);
    }

    @Override
    public void removeScriptBlock(String modName, String version, String fileName, String scriptBlockName) {
        modGenerationService.removeScriptBlock(modName, version, fileName, scriptBlockName);
    }
}
