package de.projectmodding.core.model;

import de.projectmodding.core.enums.DataType;
import de.projectmodding.core.enums.ParameterAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    String delimiter;
    String description;
    String value;
    Boolean require;
    ParameterAction action;
    Condition condition;
    Integer min;
    Integer max;

    static public class Condition{
        ArrayList<And> and;
        ArrayList<Or> or;

        static public class And{
            List<String> has = new ArrayList<>();
        }

        static public class Or{
            List<String> has = new ArrayList<>();
        }
    }
}

