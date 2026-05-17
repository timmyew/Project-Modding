package de.projectmodding.core.model.definition;

import de.projectmodding.core.enums.ModDataEnum;
import de.projectmodding.core.model.AttributeModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class FileDefinitionModel {
    List<CustomTypeMappings> customTypeMappings;
    HashMap<String, List<String>> customDataTypeMap;
    String filePath;
    List<AttributeModel> attributes;

    public static FileDefinitionModel copy(FileDefinitionModel model) {
        return FileDefinitionModel.builder()
                .customTypeMappings(model.getCustomTypeMappings().stream().map(CustomTypeMappings::copy).collect(Collectors.toCollection(ArrayList::new)))
                .customDataTypeMap(createCustomDataTypeMap(model.getCustomDataTypeMap()))
                .filePath(model.filePath)
                .attributes(model.attributes.stream().map(AttributeModel::copy).collect(Collectors.toCollection(ArrayList::new)))
                .build();
    }

    private static HashMap<String, List<String>> createCustomDataTypeMap(HashMap<String, List<String>> customDataTypeMap) {
        HashMap<String, List<String>> result = new HashMap<>();

        customDataTypeMap.forEach((key, value) -> {
            result.put(key, value.stream().map(String::new).collect(Collectors.toCollection(ArrayList::new)));
        });
        return result;
    }

    @Value
    @NoArgsConstructor(force = true)
    @AllArgsConstructor
    static public class CustomTypeMappings{
        String parent;
        String child;

        private static CustomTypeMappings copy(CustomTypeMappings customTypeMappings) {
            return new CustomTypeMappings(customTypeMappings.getParent(), customTypeMappings.getChild());
        }
    }
}
