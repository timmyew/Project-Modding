package de.projectmodding.core.controller;

import de.projectmodding.core.model.mod.ModPackageModel;

import java.util.List;

public interface INewPackageController {
    public String chooseLocation();
    public ModPackageModel createPackage(String packageName, String rootPath, String version, String packageVersion);
    public List<String> loadGameVersionList();
}
