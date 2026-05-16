package de.projectmodding.core.model;

import de.projectmodding.core.enums.DataType;
import de.projectmodding.core.enums.ParameterAction;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    String internalData;

    public static AttributeModel copy(AttributeModel attributeModel){
        return AttributeModel.builder()
                .keyName(attributeModel.getKeyName())
                .dataType(attributeModel.getDataType())
                .subType(attributeModel.getSubType())
                .customType(attributeModel.getCustomType())
                .displayName(attributeModel.getDisplayName())
                .delimiter(attributeModel.getDelimiter())
                .description(attributeModel.getDescription())
                .value(attributeModel.getValue())
                .required(attributeModel.getRequired())
                .action(attributeModel.getAction())
                .condition(Condition.copy(attributeModel.getCondition()))
                .min(attributeModel.getMin())
                .max(attributeModel.getMax())
                .build();
    }

    @NoArgsConstructor(force = true)
    @AllArgsConstructor
    @Value
    static public class Condition{
        ArrayList<And> and;
        ArrayList<Or> or;

        @NoArgsConstructor(force = true)
        @AllArgsConstructor
        @Value
        static public class And{
            List<String> has;
            public static And copy(And and){
                return new And(and.getHas().stream().map(String::new).collect(Collectors.toCollection(ArrayList::new)));
            }
        }

        @NoArgsConstructor(force = true)
        @AllArgsConstructor
        @Value
        static public class Or{
            List<String> has;
            public static Or copy(Or or){
                return new Or(or.getHas().stream().map(String::new).collect(Collectors.toCollection(ArrayList::new)));
            }
        }

        public static Condition copy(Condition condition){
            return condition != null ? new Condition(
                    condition.getAnd() != null ? condition.getAnd().stream().map(And::copy).collect(Collectors.toCollection(ArrayList::new)) : null,
                    condition.getOr() != null ? condition.getOr().stream().map(Or::copy).collect(Collectors.toCollection(ArrayList::new)) : null
            ) : null;
        }
    }
}

