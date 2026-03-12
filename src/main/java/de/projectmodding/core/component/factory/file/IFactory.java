package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.model.definition.ModDefinitionModel;

public interface IFactory<T> {


    T create(ModDefinitionModel modDefinitionModel, String relatedPath);

}
