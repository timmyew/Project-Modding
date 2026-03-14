package de.projectmodding.core.component.validator;

import de.projectmodding.core.enums.DataType;
import de.projectmodding.core.exception.DefinitionDetailModelValidatorException;
import de.projectmodding.core.model.definition.FileDefinitionModel;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class DefinitionDetailModelValidator {
    private static final String DOT_DELIMITER = java.util.regex.Pattern.quote(".");
    private DefinitionDetailModelValidator() {
    }

    public static void validate(FileDefinitionModel detailModel) {
        validateCustomDataTypeMap(detailModel);
        validateCustomTypeMappings(detailModel);
    }

    private static void validateCustomDataTypeMap(FileDefinitionModel detailModel) {
        AtomicReference<HashMap<String, List<String>>> atomicCustomDataTypes = new AtomicReference<>(detailModel.getCustomDataTypeMap());

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

    private static void validateCustomTypeMappings(FileDefinitionModel detailModel) {
        detailModel.getCustomTypeMappings().forEach(mapper -> {
            String[] pairOrOne = mapper.getParent().split(DOT_DELIMITER);

            if (pairOrOne.length == 1) {
                String typeName = pairOrOne[0];
            }
            else if (pairOrOne.length == 2) {
                String typeName = pairOrOne[0];
                String typeElement = pairOrOne[1];
            }
        });
    }

    private void validateStringValue(String value){
        if (value == null){

        }
        else if (value.isBlank()){

        }
    }
}
