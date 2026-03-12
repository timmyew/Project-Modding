package de.projectmodding.core.component.validator;

import de.projectmodding.core.constant.definition.AttributeConstants;
import de.projectmodding.core.enums.DataType;
import de.projectmodding.core.enums.ParameterAction;
import de.projectmodding.core.exception.AttributeValidationException;
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
        }
    }

    private static void validateAction(JSONObject jsonObject, String context) {
        if (jsonObject.has(AttributeConstants.ACTION)) {
            String actionValue = jsonObject.getString(AttributeConstants.ACTION);

            if (actionValue != null && !actionValue.isBlank()) {
                validateAttribute(jsonObject, AttributeConstants.ACTION, context);

                if (!ParameterAction.isEnum(actionValue)) {
                    throw new AttributeValidationException(
                            String.format("The value of attribute '%s' can't be '%s'. \n Available values can be found in the documentation. \n%s",
                                    AttributeConstants.ACTION, actionValue, context)
                    );
                }
            }
        }
    }

    private static void validateAttribute(JSONObject jsonObject, String attributeName, String context) {
        if (jsonObject.has(attributeName)) {
            validateValue(jsonObject.getString(attributeName), attributeName, context);
        } else
            throw new AttributeValidationException(String.format(DEFAULT_ERROR_MESSAGE, attributeName, context));
    }

    private static void validateValue(String value, String attributeName, String context) {
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
}
