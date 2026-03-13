package de.projectmodding.core.controller;

import de.projectmodding.core.model.mod.files.data.ScriptBlock;

public interface IScriptPanelController {
    ScriptBlock createScriptBlock(String modName, String version, String fileName, String scriptBlockName);
    void removeScriptBlock(String modName, String version, String fileName, String scriptBlockName);
}
