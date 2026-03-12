package de.projectmodding.core.util;

import de.projectmodding.core.model.AttributeModel;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class AttributeUtils {
    private AttributeUtils(){}

    public static AttributeModel getAttribute(String keyName, List<AttributeModel> attributes){
        AtomicReference<AttributeModel> resultAttribute = new AtomicReference<>();

        attributes.forEach(attribute->{
            if (attribute.getKeyName().equalsIgnoreCase(keyName)) {
                resultAttribute.set(attribute);
            }
        });

        return resultAttribute.get();
    }
}
