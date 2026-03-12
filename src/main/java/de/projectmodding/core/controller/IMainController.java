package de.projectmodding.core.controller;

import de.projectmodding.core.enums.ModDataKey;
import de.projectmodding.core.model.mod.ModPackageModel;
import de.projectmodding.core.model.mod.files.BaseFile;

import javax.swing.tree.DefaultTreeModel;
import java.util.List;

public interface IMainController {
    void loadDefinition();

    ModPackageModel getPackageModel();

    DefaultTreeModel generateTreeModel(ModPackageModel packageModel);

    BaseFile generateModData(String version, String modName, ModDataKey key);

    List<String> getItemList();
}