package de.projectmodding.core.model.definition;

import de.projectmodding.core.enums.ModDataEnum;
import de.projectmodding.core.model.AttributeModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.HashMap;
import java.util.List;

@Value
@Builder
public class FileDefinitionModel {
    List<CustomTypeMappings> customTypeMappings;
    HashMap<String, List<String>> customDataTypeMap;
    String filePath;
    List<AttributeModel> attributes;

    @Value
    @NoArgsConstructor(force = true)
    @AllArgsConstructor
    static public class CustomTypeMappings{
        String parent;
        String child;
    }
}
