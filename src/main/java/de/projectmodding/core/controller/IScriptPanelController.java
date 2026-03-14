package de.projectmodding.core.controller;

import de.projectmodding.core.model.mod.files.data.ScriptBlock;

import java.util.HashMap;
import java.util.List;

public interface IScriptPanelController {
    ScriptBlock createScriptBlock(String modName, String version, String fileName, String scriptBlockName);
    void removeScriptBlock(String modName, String version, String fileName, String scriptBlockName);
    HashMap<String, List<String>> getScriptBlockTypes(String version);
}
