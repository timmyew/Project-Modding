package de.projectmodding.core.enums;

public enum ParameterAction {
    Transform_Name("Transform_Name"),
    Transform_Version("Transform_Version"),
    First_Required_Attribute("First_Required_Attribute");

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
