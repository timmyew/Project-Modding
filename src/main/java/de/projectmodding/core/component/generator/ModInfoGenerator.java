package de.projectmodding.core.component.generator;

import de.projectmodding.core.component.validator.ModInfoValidator;
import de.projectmodding.core.constant.definition.AttributeConstants;
import de.projectmodding.core.enums.DataType;
import de.projectmodding.core.model.AttributeModel;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class ModInfoGenerator {
    private final static String VALUE_ASSIGNMENT = "=";

    private ModInfoGenerator() {
    }

    public static String generate(List<AttributeModel> modInfoAttributes) {
        AtomicReference<StringBuilder> atomicBuilder = new AtomicReference<>();
        atomicBuilder.set(new StringBuilder());

        ModInfoValidator.validate(modInfoAttributes);

        modInfoAttributes.forEach(attribute -> {
            if (attribute.getValue() != null && !attribute.getValue().isBlank()) {
                if (attribute.getDataType().equals(DataType.Array)) {
                    atomicBuilder.get().append(generateArrayLine(attribute));
                } else {
                    atomicBuilder.get().append(generateLine(attribute.getKeyName(), attribute.getValue()));
                }
            }
        });
        return atomicBuilder.get().toString();
    }

    private static String generateArrayLine(AttributeModel attribute) {
        return generateLine(attribute.getKeyName(),
                attribute.getValue().replace(AttributeConstants.GLOBAL_DELIMITER, attribute.getDelimiter())
        );
    }

    private static String generateLine(String keyName, String data) {
        return keyName.concat(VALUE_ASSIGNMENT).concat(data).concat(System.lineSeparator());
    }
}
