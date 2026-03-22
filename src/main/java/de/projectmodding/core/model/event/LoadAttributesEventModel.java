package de.projectmodding.core.model.event;

import de.projectmodding.core.model.definition.FileDefinitionModel;
import de.projectmodding.core.model.mod.files.data.ScriptBlock;
import de.projectmodding.gui.manager.DatatypeComponentManager;
import lombok.Builder;
import lombok.Value;

import java.util.HashMap;
import java.util.List;

@Value
@Builder
public class LoadAttributesEventModel {
    //Type to element or list of elements
    HashMap<String, HashMap<String, List<String>>> blockTypeItemTypeMap;
    String moduleName;
    ScriptBlock scriptBlock;
    FileDefinitionModel definitionModel;
    DatatypeComponentManager componentManager;
}
