package de.projectmodding.core.component.factory.file.scriptblock;

import de.projectmodding.core.model.mod.files.data.ScriptBlock;

import java.util.ArrayList;

public class ScriptBlockFactory {
    private ScriptBlockFactory(){}

    public static ScriptBlock createScriptBlock(String name) {
        return ScriptBlock.builder()
                .name(name)
                .blockType("")
                .attributes(new ArrayList<>())
                .build();
    }
}
