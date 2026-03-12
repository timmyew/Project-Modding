package de.projectmodding.core.model.definition;

import de.projectmodding.core.model.AttributeModel;
import lombok.Builder;
import lombok.Value;

import java.util.HashMap;
import java.util.List;

@Value
@Builder
public class FileDefinitionModel {
    HashMap<String, List<String>> customDatatypeMap;
    String filePath;
    List<AttributeModel> attributes;
}
