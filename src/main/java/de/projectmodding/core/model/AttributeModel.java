package de.projectmodding.core.model;

import de.projectmodding.core.enums.DataType;
import de.projectmodding.core.enums.ParameterAction;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AttributeModel{
    String keyName;
    DataType dataType;
    DataType subType;
    String customType;
    String displayName;
    String delimiter;
    String description;
    String value;
    Boolean required;
    ParameterAction action;
    Condition condition;
    Integer min;
    Integer max;

    @NoArgsConstructor(force = true)
    @AllArgsConstructor
    @Value
    static public class Condition{
        ArrayList<And> and;
        ArrayList<Or> or;

        @NoArgsConstructor(force = true)
        @Value
        static public class And{
            List<String> has = new ArrayList<>();
        }

        @NoArgsConstructor(force = true)
        @Value
        static public class Or{
            List<String> has = new ArrayList<>();
        }
    }
}

