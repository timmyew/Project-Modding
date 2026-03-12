package de.projectmodding.core.constant.definition;

import java.io.File;

public final class DefinitionFileConstants {

    private DefinitionFileConstants(){}

    private static final String BASE_PATH = "configuration".concat(File.separator);
    public static final String VERSION_VAR = "{VERSION}";
    private static final String BASE_VERSION_PATH = BASE_PATH.concat(VERSION_VAR.concat(File.separator));
    public static final String VERSION = BASE_PATH.concat("Version.json");
    public static String MOD_INFO = BASE_VERSION_PATH.concat("ModInfoDef.json");
    public static String WORKSHOP = BASE_VERSION_PATH.concat("WorkshopDef.json");
    public static String SCRIPT = BASE_VERSION_PATH.concat("ScriptDef.json");
    public static String MOD_FOLDER = BASE_VERSION_PATH.concat("ModFolderDef.json");
}
