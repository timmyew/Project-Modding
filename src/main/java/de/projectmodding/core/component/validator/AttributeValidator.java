package de.projectmodding.core.component.validator;

import de.projectmodding.core.constant.definition.AttributeConstants;
import de.projectmodding.core.enums.DataType;
import de.projectmodding.core.enums.ParameterAction;
import de.projectmodding.core.exception.AttributeValidationException;
import de.projectmodding.core.model.AttributeModel;
import org.json.JSONArray;
import org.json.JSONObject;

public final class AttributeValidator {

    private static final String DEFAULT_ERROR_MESSAGE = "Attribute '%s' is missing!\n%s";

    private AttributeValidator() {
    }

    public static void validate(JSONObject jsonObject, String context) {
        validateKeyName(jsonObject, context);
        validateDataType(jsonObject, context);
        validateSubType(jsonObject, context);
        validateAction(jsonObject, context);
        validateCondition(jsonObject, context);
        validateMinMax(jsonObject, context);
    }

    private static void validateMinMax(JSONObject jsonObject, String context) {
        if (jsonObject.has(AttributeConstants.MIN) || jsonObject.has(AttributeConstants.MAX)) {
            validateAttribute(jsonObject, AttributeConstants.MIN, context);
            validateAttribute(jsonObject, AttributeConstants.MAX, context);
        }
    }

    private static void validateCondition(JSONObject jsonObject, String context) {
        if (jsonObject.has(AttributeConstants.CONDITION)) {
            Object value = jsonObject.get(AttributeConstants.CONDITION);

            if (value instanceof JSONObject jsonObj) {
                if (!validateAndOr(jsonObj, context, AttributeConstants.AND) && !validateAndOr(jsonObj, context, AttributeConstants.OR))
                    throw new AttributeValidationException(String.format("Condition requires a boolean operator.\n%s", context));
            } else
                throw new AttributeValidationException(String.format("The condition requires a '%s' object.\n%s", AttributeModel.Condition.class.getSimpleName(), context));
        }
    }

    private static boolean validateAndOr(JSONObject jsonObject, String context, String andOrKey) {
        boolean isValid = false;

        if (jsonObject.has(andOrKey) && jsonObject.get(andOrKey) instanceof JSONArray array && !array.isEmpty()) {
            isValid = true;
            array.forEach(obj -> {
                if (obj instanceof JSONObject jsonObj) {
                    validateHas(jsonObj, context);
                }
            });
        }
        return isValid;
    }

    private static void validateHas(JSONObject jsonObject, String context) {
        if (!jsonObject.has(AttributeConstants.HAS))
            throw new AttributeValidationException(String.format(DEFAULT_ERROR_MESSAGE, AttributeConstants.HAS, context));
        else if (jsonObject.get(AttributeConstants.HAS) instanceof JSONArray array) {
            if (array.isEmpty())
                throw new AttributeValidationException(String.format("Attribute '%s' requires a non-empty array in its condition when defined.\n%s", AttributeConstants.HAS, context));
            else
                array.forEach(obj -> {
                    if (obj instanceof String str) {
                        validateStringValue(str, AttributeConstants.HAS, context);
                    } else
                        throw new AttributeValidationException(String.format("Value '%s' in '%s' array, is not a String.\n%s", obj.toString(), AttributeConstants.HAS, context));
                });
        }
    }

    private static void validateKeyName(JSONObject jsonObject, String context) {
        validateAttribute(jsonObject, AttributeConstants.KEY_NAME, context);
    }

    private static void validateDataType(JSONObject jsonObject, String context) {
        validateAttribute(jsonObject, AttributeConstants.DATA_TYPE, context);
    }

    private static void validateSubType(JSONObject jsonObject, String context) {
        if (jsonObject.getString(AttributeConstants.DATA_TYPE).equalsIgnoreCase(DataType.Array.name())) {
            validateAttribute(jsonObject, AttributeConstants.SUB_TYPE, context);
            validateAttribute(jsonObject, AttributeConstants.DELIMITER, context);
        }
    }

    private static void validateAction(JSONObject jsonObject, String context) {
        if (jsonObject.has(AttributeConstants.ACTION)) {
            Object actionValue = jsonObject.get(AttributeConstants.ACTION);

            if (actionValue instanceof String str) {
                validateAttribute(jsonObject, AttributeConstants.ACTION, context);

                if (!ParameterAction.isEnum(str)) {
                    throw new AttributeValidationException(
                            String.format("The value of attribute '%s' can't be '%s'. \n Available values can be found in the documentation. \n%s",
                                    AttributeConstants.ACTION, actionValue, context)
                    );
                }
            } else
                throw new AttributeValidationException(String.format("Value of attribute '%s' is not a String.\n%s", AttributeConstants.ACTION, context));
        }
    }

    private static void validateAttribute(JSONObject jsonObject, String attributeName, String context) {
        if (jsonObject.has(attributeName)) {
            if (jsonObject.get(attributeName) instanceof String str) {
                validateStringValue(str, attributeName, context);
            }
            else if (jsonObject.get(attributeName) instanceof Integer integer) {
                validateIntegerValue(integer, attributeName, context);
            }
            else
                throw new AttributeValidationException(String.format("Invalid attribute datatype for '%s' \n%s",  attributeName, context));
        } else
            throw new AttributeValidationException(String.format(DEFAULT_ERROR_MESSAGE, attributeName, context));
    }

    private static void validateStringValue(String value, String attributeName, String context) {
        if (value == null || value.isBlank()) {
            throw new AttributeValidationException(
                    String.format("The value of attribute '%s' can't be %s!\n%s",
                            attributeName,
                            value == null ? "null" : "empty",
                            context
                    )
            );
        }
    }

    private static void validateIntegerValue(Integer value, String attributeName, String context) {
        if (value == null)
            throw new AttributeValidationException(String.format("The value of attribute '%s' can't be null!\n%s",
                    attributeName, context));
    }
}
