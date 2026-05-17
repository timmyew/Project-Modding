package de.projectmodding.core.controller;

import de.projectmodding.core.component.container.Container;
import de.projectmodding.core.enums.ModDataKey;
import de.projectmodding.core.model.definition.DefinitionVersionMap;
import de.projectmodding.core.model.mod.ModPackageModel;
import de.projectmodding.core.model.mod.files.BaseFile;
import de.projectmodding.core.model.mod.files.data.ScriptBlock;
import de.projectmodding.core.service.DefinitionService;
import de.projectmodding.core.service.ModDataGenerationService;
import de.projectmodding.core.service.RuntimeDataService;
import de.projectmodding.core.service.TreeGeneratorService;
import de.projectmodding.gui.form.MainForm;

import javax.swing.tree.DefaultTreeModel;
import java.util.List;

public class MainController implements IMainController {
    private final Container container;
    public MainController(Container mainContainer) {
        this.container = mainContainer;
    }

    @Override
    public void loadDefinition() {
        container.resolve(RuntimeDataService.class).save(container.resolve(DefinitionService.class).load());
    }

    @Override
    public ModPackageModel getPackageModel() {
        return container.resolve(RuntimeDataService.class).getByType(ModPackageModel.class);
    }

    @Override
    public DefaultTreeModel generateTreeModel(ModPackageModel packageModel) {
        return container.resolve(TreeGeneratorService.class).generateTreeModel(packageModel);
    }

    @Override
    public BaseFile generateModData(String version, String modName, ModDataKey key) {
        return container.resolve(ModDataGenerationService.class).createFile(
                container.resolve(RuntimeDataService.class).getByType(ModPackageModel.class)
                , modName, key, version
        );
    }

    @Override
    public List<ScriptBlock> getScriptBlocks(String modName, String modVersion, String fileName) {
        return container.resolve(ModDataGenerationService.class).searchAndGetScriptBlocks(modName, modVersion, fileName);
    }
}
