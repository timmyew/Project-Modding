package de.projectmodding.core.component.validator;

import de.projectmodding.core.constant.definition.ModInfoConstants;
import de.projectmodding.core.enums.DataType;
import de.projectmodding.core.exception.ModHeaderValidatorException;
import de.projectmodding.core.exception.ModInfoValidationException;
import de.projectmodding.core.model.AttributeModel;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ModInfoValidator {
    private ModInfoValidator() {
    }

    public static void validate(List<AttributeModel> modInfoAttributes) {
        AtomicBoolean isNameExists = new AtomicBoolean(false);
        AtomicBoolean isIdExists = new AtomicBoolean(false);

        modInfoAttributes.forEach(attribute -> {
            validateRequiredFieldsValue(attribute, isNameExists, isIdExists);
            validateDataType(attribute.getKeyName(), attribute.getDataType());
        });

        validateRequiredFieldsAreExisting(isNameExists, isIdExists);
    }

    private static void validateValue(String keyName, String value) {
        if (value == null || value.isBlank()) {
            throw new ModInfoValidationException(
                    String.format("Validation of required attribute '%s' failed, because the value is %s",
                            keyName, value == null ? "null" : "empty")
            );
        }
    }

    private static void validateDataType(String keyName, DataType dataType){
        if (dataType == null){
            throw new ModInfoValidationException(
                    String.format("Validation of attribute '%s' failed, because the datatype is null!", keyName)
            );
        }
    }

    private static void validateRequiredFieldsValue(AttributeModel attribute, AtomicBoolean isNameExists, AtomicBoolean isIdExists){
        if (attribute.getKeyName().equalsIgnoreCase(ModInfoConstants.NAME)) {
            validateValue(attribute.getKeyName(), attribute.getValue());
            isNameExists.set(true);
        } else if (attribute.getKeyName().equalsIgnoreCase(ModInfoConstants.ID)) {
            validateValue(attribute.getKeyName(), attribute.getValue());
            isIdExists.set(true);
        }
    }

    private static void validateRequiredFieldsAreExisting(AtomicBoolean isNameExists, AtomicBoolean isIdExists){
        String errorData = !isNameExists.get() ? ModInfoConstants.NAME : "";

        if (!isIdExists.get()){
            if (errorData.isBlank()){
                errorData = ModInfoConstants.ID;
            }
            else
            {
                errorData = errorData.concat(" and ".concat(ModInfoConstants.ID));
            }
        }

        if (!errorData.isBlank()){
            throw new ModHeaderValidatorException(String.format("Validation failed, because required attribute '%s' is missing!", errorData));
        }
    }
}
