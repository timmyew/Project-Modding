package de.projectmodding.core.component.validator;

import de.projectmodding.core.enums.DataType;
import de.projectmodding.core.exception.DefinitionDetailModelValidatorException;
import de.projectmodding.core.model.definition.FileDefinitionModel;

import java.io.File;
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
            validateStringValue(mapper.getParent(), "Parent");
            validateStringValue(mapper.getChild(), "Child");
            validateTypeMapperValue(mapper.getParent().split(DOT_DELIMITER), detailModel);
            validateTypeMapperValue(mapper.getChild().split(DOT_DELIMITER), detailModel);
        });
    }

    private static void validateTypeMapperValue(String[] pairOrOne, FileDefinitionModel detailModel) {
        String typeName = "";
        String typeElement = "";

        boolean typeNotExists = false;
        boolean elementNotExists = false;

        if (pairOrOne.length == 1) {
            typeName = pairOrOne[0];
            typeNotExists = hasNotCustomType(typeName, detailModel);
        }
        else if (pairOrOne.length == 2) {
            typeName = pairOrOne[0];
            typeElement = pairOrOne[1];
            elementNotExists = hasNotCustomTypeElement(typeName, typeElement, detailModel);
        }
        else if (pairOrOne.length > 2)
            throw new DefinitionDetailModelValidatorException("CustomTypeMappings doesn't support more than 2 inheritance.");

        if (typeNotExists)
            throw new DefinitionDetailModelValidatorException(String.format("The defined type in customTypeMappings '%s' didn't exists.", typeName));
        else if (elementNotExists)
            throw new DefinitionDetailModelValidatorException(String.format("The defined type.element in customTypeMappings '%s' didn't exists.", typeElement));
    }

    private static void validateStringValue(String value, String keyName){
        if (value == null){
            throw new DefinitionDetailModelValidatorException(String.format("'%s' %s attribute cannot be null.",
                    keyName, FileDefinitionModel.CustomTypeMappings.class.getSimpleName()));
        }
        else if (value.isBlank()){
            throw new DefinitionDetailModelValidatorException(String.format("'%s' %s attribute cannot be empty.",
                    keyName, FileDefinitionModel.CustomTypeMappings.class.getSimpleName()));
        }
    }

    private static boolean hasNotCustomType(String customType, FileDefinitionModel detailModel) {
        return !detailModel.getCustomDataTypeMap().containsKey(customType);
    }

    private static boolean hasNotCustomTypeElement(String customType, String customTypeElement, FileDefinitionModel detailModel) {
        boolean result = false;

        if (!hasNotCustomType(customType, detailModel)){
            List<String> elementList = detailModel.getCustomDataTypeMap().get(customType);

            for(String element : elementList){
                if(element.equalsIgnoreCase(customTypeElement)){
                    result = true;
                    break;
                }
            }
        }
        return !result;
    }
}
