package de.projectmodding.core.controller;

import de.projectmodding.core.component.factory.DialogFactory;
import de.projectmodding.core.component.factory.ModPackageFactory;
import de.projectmodding.core.model.definition.DefinitionVersionMap;
import de.projectmodding.core.model.mod.ModPackageModel;
import de.projectmodding.core.service.ModDataGenerationService;
import de.projectmodding.core.service.RuntimeDataService;
import de.projectmodding.gui.form.NewPackageForm;

import java.util.List;

public class NewPackageController implements INewPackageController {
    private final RuntimeDataService runtimeDataService;
    private final ModDataGenerationService modDataGenerationService;
    private final NewPackageForm view;
    public NewPackageController(NewPackageForm view, RuntimeDataService runtimeDataService, ModDataGenerationService modDataGenerationService) {
        this.view = view;
        this.runtimeDataService = runtimeDataService;
        this.modDataGenerationService = modDataGenerationService;
    }

    @Override
    public String chooseLocation() {
        return DialogFactory.createFolderFileDialogAndGetPath(view, "Choose the Project Directory");
    }

    @Override
    public ModPackageModel createPackage(String packageName, String rootPath, String version, String packageVersion) {
        ModPackageModel packageModel = ModPackageFactory.createSimple(
                packageName,
                rootPath,
                version,
                packageVersion,
                runtimeDataService.getByType(DefinitionVersionMap.class)
        );

        runtimeDataService.remove(packageModel.getClass());
        runtimeDataService.save(packageModel);
        return packageModel;
    }

    @Override
    public List<String> loadGameVersionList() {
        return runtimeDataService.getByType(DefinitionVersionMap.class).getVersionList();
    }
}
