package de.projectmodding.core;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import de.projectmodding.core.component.container.Container;
import de.projectmodding.core.component.event.system.EventSystem;
import de.projectmodding.core.component.loader.DefinitionLoader;
import de.projectmodding.core.controller.MainController;
import de.projectmodding.core.controller.NewPackageController;
import de.projectmodding.core.controller.ScriptPanelController;
import de.projectmodding.core.enums.DataType;
import de.projectmodding.core.model.mod.files.ScriptModel;
import de.projectmodding.core.service.DefinitionService;
import de.projectmodding.core.service.ModDataGenerationService;
import de.projectmodding.core.service.RuntimeDataService;
import de.projectmodding.core.service.TreeGeneratorService;
import de.projectmodding.gui.form.MainForm;
import de.projectmodding.gui.dataTypeComponent.*;
import de.projectmodding.gui.form.NewPackageForm;
import de.projectmodding.gui.generator.ScriptPanelRequiredAttributeGenerator;
import de.projectmodding.gui.manager.DatatypeComponentManager;
import de.projectmodding.gui.manager.FilePanelManager;
import de.projectmodding.gui.panel.script.ScriptPanel;

import java.util.Locale;

public class Application {
    private static Application instance;

    private boolean isInitialized = false;

    private final Container mainContainer = new Container();

    private Application() {}

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }

    public void init() {
        //Theme
        Locale.setDefault(Locale.ENGLISH);
        FlatMacDarkLaf.setup();

        //Loader
        mainContainer.register(DefinitionLoader.class, new DefinitionLoader());

        //Services
        mainContainer.register(TreeGeneratorService.class, new TreeGeneratorService(mainContainer));
        mainContainer.register(RuntimeDataService.class, new RuntimeDataService());
        mainContainer.register(ModDataGenerationService.class, new ModDataGenerationService(mainContainer));
        mainContainer.register(DefinitionService.class, new DefinitionService(mainContainer));

        //Generators
        mainContainer.register(ScriptPanelRequiredAttributeGenerator.class, new ScriptPanelRequiredAttributeGenerator(mainContainer));

        //EventSystem
        mainContainer.register(EventSystem.class, new EventSystem());

        //Controller
        mainContainer.register(MainController.class, new MainController(mainContainer));
        mainContainer.register(NewPackageController.class, new NewPackageController(mainContainer));
        mainContainer.register(ScriptPanelController.class, new ScriptPanelController(mainContainer));

        //GUI Datatype Manager
        DatatypeComponentManager datatypeComponentManager = new DatatypeComponentManager();
        datatypeComponentManager.registerComponent(DataType.Array, ArrayComponent.class);
        datatypeComponentManager.registerComponent(DataType.Boolean, BooleanComponent.class);
        datatypeComponentManager.registerComponent(DataType.Custom, CustomComponent.class);
        datatypeComponentManager.registerComponent(DataType.String, StringComponent.class);
        datatypeComponentManager.registerComponent(DataType.Float, FloatComponent.class);
        datatypeComponentManager.registerComponent(DataType.Integer, IntegerComponent.class);
        datatypeComponentManager.registerComponent(DataType.File, FileComponent.class);
        datatypeComponentManager.registerComponent(DataType.Attribute, AttributeChoiceComponent.class);
        mainContainer.register(DatatypeComponentManager.class, datatypeComponentManager);

        //GUI Panel Manager
        FilePanelManager filePanelManager = new FilePanelManager();
        filePanelManager.registerPanel(ScriptModel.class, new ScriptPanel(mainContainer));
        mainContainer.register(FilePanelManager.class, filePanelManager);

        //GUI Forms
        mainContainer.register(MainForm.class, new MainForm(mainContainer));
        mainContainer.register(NewPackageForm.class, new NewPackageForm(mainContainer));

        isInitialized = true;
    }

    public void run(){
        if (!isInitialized){
            throw new IllegalStateException("Application was not initialized!");
        }
        else{
            mainContainer.resolve(MainForm.class).showForm();
        }
    }
}
