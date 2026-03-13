package de.projectmodding.core.component.factory.file.scriptblock;

import de.projectmodding.core.model.mod.files.data.ScriptBlock;

public class ScriptBlockFactory {
    private ScriptBlockFactory(){}

    public static ScriptBlock createScriptBlock(String name) {
        return ScriptBlock.builder()
                .name(name)
                .blockType("")
                .build();
    }
}
