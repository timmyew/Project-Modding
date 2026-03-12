package de.projectmodding.core.enums;

public enum ParameterAction {
    Transform_Name("Transform_Name"),
    Transform_Version("Transform_Version"),
    Transform_Script_Type("Transform_Script_Type");

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
