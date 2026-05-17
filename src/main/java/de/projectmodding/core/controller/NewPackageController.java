package de.projectmodding.core.controller;

import de.projectmodding.core.component.container.Container;
import de.projectmodding.core.component.factory.DialogFactory;
import de.projectmodding.core.component.factory.ModPackageFactory;
import de.projectmodding.core.model.definition.DefinitionVersionMap;
import de.projectmodding.core.model.mod.ModPackageModel;
import de.projectmodding.core.service.ModDataGenerationService;
import de.projectmodding.core.service.RuntimeDataService;
import de.projectmodding.gui.form.NewPackageForm;

import java.util.List;

public class NewPackageController implements INewPackageController {
    private final Container container;
    public NewPackageController(Container mainContainer) {
        this.container =  mainContainer;
    }

    @Override
    public String chooseLocation() {
        return DialogFactory.createFolderFileDialogAndGetPath(container.resolve(NewPackageForm.class), "Choose the Project Directory");
    }

    @Override
    public ModPackageModel createPackage(String packageName, String rootPath, String version, String packageVersion) {
        ModPackageModel packageModel = ModPackageFactory.createSimple(
                packageName,
                rootPath,
                version,
                packageVersion,
                container.resolve(RuntimeDataService.class).getByType(DefinitionVersionMap.class)
        );

        RuntimeDataService runtimeDataService = container.resolve(RuntimeDataService.class);
        runtimeDataService.remove(packageModel.getClass());
        runtimeDataService.save(packageModel);
        return packageModel;
    }

    @Override
    public List<String> loadGameVersionList() {
        return container.resolve(RuntimeDataService.class).getByType(DefinitionVersionMap.class).getVersionList();
    }
}
