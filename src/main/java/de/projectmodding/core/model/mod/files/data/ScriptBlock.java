package de.projectmodding.core.model.mod.files.data;

import de.projectmodding.core.model.AttributeModel;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
public class ScriptBlock {
    String name;
    String blockType;
    List<AttributeModel> attributes;
}
