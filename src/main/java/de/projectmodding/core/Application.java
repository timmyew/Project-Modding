package de.projectmodding.core;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import de.projectmodding.core.component.event.system.EventSystem;
import de.projectmodding.core.controller.MainController;
import de.projectmodding.core.controller.NewPackageController;
import de.projectmodding.core.controller.ScriptPanelController;
import de.projectmodding.core.enums.DataType;
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
import java.util.Locale;

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
        Locale.setDefault(Locale.ENGLISH);
        FlatMacDarkLaf.setup();

        //Services
        TreeGeneratorService treeGeneratorService = new TreeGeneratorService();
        RuntimeDataService runtimeDataService = new RuntimeDataService();
        ModGenerationService modGenerationService = new ModGenerationService(runtimeDataService);
        DefinitionService definitionService = new DefinitionService();

        //EventSystem
        EventSystem eventSystem = EventSystem.getInstance();

        //GUI Datatype Manager
        DatatypeComponentManager datatypeComponentManager = new DatatypeComponentManager();
        datatypeComponentManager.registerComponent(DataType.Array, ArrayComponent.class);
        datatypeComponentManager.registerComponent(DataType.Boolean, BooleanComponent.class);
        datatypeComponentManager.registerComponent(DataType.Custom, CustomComponent.class);
        datatypeComponentManager.registerComponent(DataType.String, StringComponent.class);
        datatypeComponentManager.registerComponent(DataType.Float, FloatComponent.class);
        datatypeComponentManager.registerComponent(DataType.Integer, IntegerComponent.class);
        datatypeComponentManager.registerComponent(DataType.File, FileComponent.class);

        ScriptPanelController scriptPanelController = new ScriptPanelController(runtimeDataService, modGenerationService);

        //GUI Panel Manager
        FilePanelManager filePanelManager = new FilePanelManager();
        filePanelManager.registerPanel(ScriptModel.class, new ScriptPanel(eventSystem, datatypeComponentManager, scriptPanelController));

        //GUI Forms
        mainForm = new MainForm(eventSystem, filePanelManager);

        //Controller
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
