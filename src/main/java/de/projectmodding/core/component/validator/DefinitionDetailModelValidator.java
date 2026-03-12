package de.projectmodding.core.component.validator;

import de.projectmodding.core.enums.DataType;
import de.projectmodding.core.exception.DefinitionDetailModelValidatorException;
import de.projectmodding.core.model.definition.FileDefinitionModel;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class DefinitionDetailModelValidator {
    private DefinitionDetailModelValidator() {
    }

    public static void validate(FileDefinitionModel detailModel) {
        AtomicReference<HashMap<String, List<String>>> atomicCustomDataTypes = new AtomicReference<>(detailModel.getCustomDatatypeMap());

        detailModel.getAttributes().forEach(attribute -> {
            if (attribute.getDataType().equals(DataType.Custom) && !atomicCustomDataTypes.get().containsKey(attribute.getCustomType())) {
                throw new DefinitionDetailModelValidatorException(
                        String.format(
                                "Cannot map custom datatype of attribute '%s' to '%s', because the custom datatype '%s' didn't exists!",
                                attribute.getKeyName(),
                                attribute.getCustomType(),
                                attribute.getCustomType()
                        )
                );
            }
        });
    }
}
