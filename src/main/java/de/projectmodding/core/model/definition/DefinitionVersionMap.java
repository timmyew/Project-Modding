package de.projectmodding.core.model.definition;

import de.projectmodding.core.component.query.definition.DefinitionQuery;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
@Builder
public class DefinitionVersionMap {
    HashMap<String, ModDefinitionModel> map;
    List<String> versionList;
}
