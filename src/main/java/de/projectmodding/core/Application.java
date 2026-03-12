package de.projectmodding.core;

import com.formdev.flatlaf.FlatDarkLaf;
import de.projectmodding.core.component.event.system.EventSystem;
import de.projectmodding.core.controller.MainController;
import de.projectmodding.core.controller.NewPackageController;
import de.projectmodding.core.enums.DataType;
import de.projectmodding.core.enums.ModDataKey;
import de.projectmodding.core.model.mod.files.ScriptModel;
import de.projectmodding.core.service.DefinitionService;
import de.projectmodding.core.service.ModGenerationService;
import de.projectmodding.core.service.RuntimeDataService;
import de.projectmodding.core.service.TreeGeneratorService;
import de.projectmodding.gui.MainForm;
import de.projectmodding.gui.dataTypeComponent.*;
import de.projectmodding.gui.manager.DatatypeComponentManager;
import de.projectmodding.gui.manager.FilePanelManager;
import de.projectmodding.gui.panel.ScriptPanel;

import javax.swing.*;

public class Application {
    private static Application instance;

    private boolean isInitialized = false;

    private MainForm mainForm;
    private Application() {}

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }

    public void init() {
        EventSystem eventSystem = EventSystem.getInstance();

        DatatypeComponentManager datatypeComponentManager = new DatatypeComponentManager();
        datatypeComponentManager.registerComponent(DataType.Array, new ArrayComponent());
        datatypeComponentManager.registerComponent(DataType.Boolean, new BooleanComponent());
        datatypeComponentManager.registerComponent(DataType.Custom, new CustomComponent());
        datatypeComponentManager.registerComponent(DataType.String, new StringComponent());
        datatypeComponentManager.registerComponent(DataType.Float, new FloatComponent());
        datatypeComponentManager.registerComponent(DataType.Integer, new IntegerComponent());
        datatypeComponentManager.registerComponent(DataType.File, new FileComponent());

        FilePanelManager filePanelManager = new FilePanelManager();
        filePanelManager.registerPanel(ScriptModel.class, new ScriptPanel(eventSystem));

        mainForm = new MainForm(eventSystem, filePanelManager);

        ModGenerationService modGenerationService = new ModGenerationService();
        TreeGeneratorService treeGeneratorService = new TreeGeneratorService();
        RuntimeDataService runtimeDataService = new RuntimeDataService();
        DefinitionService definitionService = new DefinitionService();

        MainController mainController = new MainController(mainForm, runtimeDataService, definitionService, treeGeneratorService, modGenerationService);
        NewPackageController newPackageController = new NewPackageController(mainForm.getNewPackageForm(), runtimeDataService, modGenerationService);

        mainForm.setController(mainController);
        mainForm.getNewPackageForm().setController(newPackageController);

        isInitialized = true;
    }

    public void run(){
        if (!isInitialized){
            throw new IllegalStateException("Application was not initialized!");
        }
        else{
            mainForm.showForm();
        }
    }
}
