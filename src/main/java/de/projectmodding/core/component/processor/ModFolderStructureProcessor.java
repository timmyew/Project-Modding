package de.projectmodding.core.component.processor;

import de.projectmodding.core.model.AttributeModel;
import de.projectmodding.core.model.definition.DefinitionVersionMap;

import java.util.HashMap;
import java.util.List;

public final class ModFolderStructureProcessor {
    private static final char PLACEHOLDER_BRACKET_BEGIN = '{';
    private static final char PLACEHOLDER_BRACKET_END = '}';
    private static final int NOT_FOUND_FLAG = -1;

    public static void process(DefinitionVersionMap definitionVersionMap) {
        definitionVersionMap.getMap().forEach((key, value) -> {
            processAttributes(value.getModFolder().getAttributes());
        });
    }

    private static void processAttributes(List<AttributeModel> attributes) {
        HashMap<String, String> nameValueMap = new HashMap<>();
        int maxIteration = attributes.size() * attributes.size();
        int count = 0;

        while (nameValueMap.size() < attributes.size() && count <= maxIteration) {
            for (AttributeModel attribute : attributes) {
                String key = attribute.getKeyName();
                String value = attribute.getValue();

                int bracketBeginPos = value.indexOf(PLACEHOLDER_BRACKET_BEGIN);
                int bracketEndPos = value.indexOf(PLACEHOLDER_BRACKET_END);

                //If no bracket there
                if (bracketBeginPos == NOT_FOUND_FLAG && bracketEndPos == NOT_FOUND_FLAG) {
                    nameValueMap.put(key, value);
                } else {
                    String varKeyWithBracket = value.substring(bracketBeginPos, bracketEndPos + 1);
                    String varKey = String.copyValueOf(varKeyWithBracket.toCharArray(), 1, varKeyWithBracket.length() - 2);

                    if (nameValueMap.containsKey(varKey)){
                        attribute.setValue(
                                value.replace(varKeyWithBracket, nameValueMap.get(varKey))
                        );
                    }
                }
            }
            count++;
        }
    }
}
