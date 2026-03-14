package de.projectmodding.core.model.event;

import de.projectmodding.core.model.mod.files.data.ScriptBlock;
import lombok.Builder;
import lombok.Value;

import java.util.HashMap;
import java.util.List;

@Value
@Builder
public class LoadAttributesEventModel {
    HashMap<String, List<String>> blockTypeItemTypeMap;
    ScriptBlock scriptBlock;
}
