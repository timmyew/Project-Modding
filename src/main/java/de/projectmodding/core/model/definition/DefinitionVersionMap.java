package de.projectmodding.core.model.definition;

import de.projectmodding.core.component.query.definition.DefinitionQuery;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class DefinitionVersionMap {
    HashMap<String, ModDefinitionModel> map;
    List<String> versionList;

    public static DefinitionVersionMap copy(DefinitionVersionMap definitionVersionMap) {
        return DefinitionVersionMap.builder()
                .map(copyMap(definitionVersionMap.getMap()))
                .versionList(definitionVersionMap.getVersionList().stream().map(String::new).collect(Collectors.toCollection(ArrayList::new)))
                .build();
    }

    private static HashMap<String, ModDefinitionModel> copyMap(HashMap<String, ModDefinitionModel> map) {
        HashMap<String, ModDefinitionModel> copyMap = new HashMap<>();
        map.forEach((key, value) -> {
            copyMap.put(key, ModDefinitionModel.copy(value));
        });
        return copyMap;
    }
}
