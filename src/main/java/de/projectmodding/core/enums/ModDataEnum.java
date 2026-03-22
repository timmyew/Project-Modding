package de.projectmodding.core.enums;

import lombok.Getter;

public enum ModDataEnum {
    MOD("Mods"),
    COMMON("common"),
    ANIMATION("Animations"),
    MODEL_X("Model_X"),
    SANDBOX("Sandbox"),
    SCRIPT("Scripts"),
    LUA("Lua"),
    SOUND("Sounds"),
    TEXTURE("Texture"),
    MOD_INFO("ModInfo"),
    WORKSHOP("Workshop"),
    TRANSLATION("Translation");

    @Getter
    private final String value;
    ModDataEnum(String value) {
        this.value = value;
    }

}
