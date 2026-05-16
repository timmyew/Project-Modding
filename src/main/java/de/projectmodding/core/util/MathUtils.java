package de.projectmodding.core.util;

public class MathUtils {
    private MathUtils(){}

    public static Integer getPercentValue(Integer baseValue, Integer percentage){
        return (baseValue / 100) * percentage;
    }

    public static int tryParseInt(String string, int defaultValue) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static int tryParseInt(String string) {
        return tryParseInt(string, 0);
    }
}
