package de.projectmodding.core.util;

public class MathUtils {
    private MathUtils(){}

    public static Integer getPercentValue(Integer baseValue, Integer percentage){
        return (baseValue / 100) * percentage;
    }
}
