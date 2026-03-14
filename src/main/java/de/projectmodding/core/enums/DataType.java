package de.projectmodding.core.enums;

public enum DataType {
    String("String"),
    Array("Array"),
    Integer("Integer"),
    Float("Float"),
    Boolean("Boolean"),
    File("File"),
    Custom("Custom"),
    Attribute("Attribute");

    private final String value;

    public static Boolean isEnum(String str){
        for(DataType dataType : DataType.values()){
            if(dataType.name().equals(str)){
                return true;
            }
        }
        return false;
    }

    DataType(String value){
        this.value = value;
    }
}
