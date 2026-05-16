package de.projectmodding.core.enums;

public enum ParameterAction {
    Load_Item_Types("Load_Item_Types"),
    Load_Script_Types("Load_Script_Types"),
    Transform_Name("Transform_Name"),
    Transform_Version("Transform_Version");

    private final String value;

    public static Boolean isEnum(String str){
        for(ParameterAction action : ParameterAction.values()){
            if(action.name().equals(str)){
                return true;
            }
        }
        return false;
    }
    ParameterAction(String value){
        this.value = value;
    }
}
