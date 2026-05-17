package de.projectmodding.core.component.query.definition;

import de.projectmodding.core.component.query.AbstractQuery;
import de.projectmodding.core.exception.QueryException;
import de.projectmodding.core.model.definition.FileDefinitionModel;
import de.projectmodding.core.model.definition.ModDefinitionModel;

import java.util.function.Function;

public class ModDefinitionQuery extends AbstractQuery<ModDefinitionModel> {
    ModDefinitionQuery(ModDefinitionModel model) {
        super(model);
    }

    public <V> FileDefinitionModelQuery with(Function<ModDefinitionModel, V> getter){
        Object obj = getter.apply(model);
        if (obj instanceof FileDefinitionModel fileDefinitionModel)
            return new FileDefinitionModelQuery(fileDefinitionModel);
        else throw new QueryException(String.format("%s is not a FileDefinitionModel", obj.getClass()));
    }
}
