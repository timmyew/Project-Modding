package de.projectmodding.core.controller;

import de.projectmodding.core.enums.ModDataKey;
import de.projectmodding.core.model.definition.DefinitionVersionMap;
import de.projectmodding.core.model.mod.ModPackageModel;
import de.projectmodding.core.model.mod.files.BaseFile;
import de.projectmodding.core.model.mod.files.data.ScriptBlock;
import de.projectmodding.core.service.DefinitionService;
import de.projectmodding.core.service.ModGenerationService;
import de.projectmodding.core.service.RuntimeDataService;
import de.projectmodding.core.service.TreeGeneratorService;
import de.projectmodding.gui.MainForm;

import javax.swing.tree.DefaultTreeModel;
import java.util.List;

public class MainController implements IMainController {
    private final MainForm view;
    private final RuntimeDataService runtimeDataService;
    private final DefinitionService definitionService;
    private final TreeGeneratorService treeGeneratorService;
    private final ModGenerationService modGenerationService;

    public MainController(MainForm view, RuntimeDataService runtimeDataService, DefinitionService definitionService,
                          TreeGeneratorService treeGeneratorService, ModGenerationService modGenerationService) {
        this.view = view;
        this.runtimeDataService = runtimeDataService;
        this.definitionService = definitionService;
        this.treeGeneratorService = treeGeneratorService;
        this.modGenerationService = modGenerationService;
    }

    @Override
    public void loadDefinition() {
        DefinitionVersionMap definitionVersionMap = definitionService.load();
        runtimeDataService.save(definitionVersionMap);
    }

    @Override
    public ModPackageModel getPackageModel() {
        return runtimeDataService.getByType(ModPackageModel.class);
    }

    @Override
    public DefaultTreeModel generateTreeModel(ModPackageModel packageModel) {
        return treeGeneratorService.generateTreeModel(packageModel);
    }

    @Override
    public BaseFile generateModData(String version, String modName, ModDataKey key) {
        ModPackageModel modPackageModel = runtimeDataService.getByType(ModPackageModel.class);
        return modGenerationService.createFile(modPackageModel, modName, key, version);
    }

    @Override
    public List<ScriptBlock> getScriptBlocks(String modName, String modVersion, String fileName) {
        return modGenerationService.searchAndGetScriptBlocks(modName, modVersion, fileName);
    }
}
