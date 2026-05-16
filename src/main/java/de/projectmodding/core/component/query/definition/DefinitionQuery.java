package de.projectmodding.core.component.query.definition;

import de.projectmodding.core.component.query.AbstractQuery;
import de.projectmodding.core.exception.QueryException;
import de.projectmodding.core.model.definition.DefinitionVersionMap;

public class DefinitionQuery extends AbstractQuery<DefinitionVersionMap> {

    protected DefinitionQuery(DefinitionVersionMap model) {
        super(model);
    }

    public static DefinitionQuery on(DefinitionVersionMap definitionVersionMap) {
        return new DefinitionQuery(definitionVersionMap);
    }


    public ModDefinitionQuery byVersion(String version) {
        if (model.getMap().containsKey(version)) {
            return new ModDefinitionQuery(model.getMap().get(version));
        }
        else
            throw new QueryException(String.format("Version '%s' does not exist.'", version != null ? version : "NULL"));
    }

}
