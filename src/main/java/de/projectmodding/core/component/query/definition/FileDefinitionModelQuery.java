package de.projectmodding.core.component.query.definition;

import de.projectmodding.core.component.query.AbstractQuery;
import de.projectmodding.core.exception.QueryException;
import de.projectmodding.core.model.AttributeModel;
import de.projectmodding.core.model.definition.FileDefinitionModel;
import de.projectmodding.core.model.intern.Pair;
import java.util.HashMap;
import java.util.List;

public class FileDefinitionModelQuery extends AbstractQuery<FileDefinitionModel> {
    protected FileDefinitionModelQuery(FileDefinitionModel model) {
        super(model);
    }

    public FileDefinitionModelQuery sortAttributesByConditions(HashMap<String, String> hasStates){
        if (hasStates != null && !hasStates.isEmpty()){
            model.getAttributes().removeIf(attributeModel -> {
                return !evaluateCondition(attributeModel.getCondition(), hasStates, attributeModel.getKeyName());
            });
        }

        return this;
    }

    private boolean evaluateCondition(AttributeModel.Condition condition, HashMap<String, String> hasStates, String attributeName) {
        if (condition == null)
            return true;
        else {
            return evaluateAndCondition(condition.getAnd(), hasStates, attributeName) && evaluateOrCondition(condition.getOr(), hasStates, attributeName);
        }
    }

    private boolean evaluateAndCondition(List<AttributeModel.Condition.And> andList, HashMap<String, String> hasStates, String attributeName) {
        if (andList != null && !andList.isEmpty()) {
            for (AttributeModel.Condition.And and : andList) {
                if (and.getHas() != null && !and.getHas().isEmpty()){
                    for (String hasValue : and.getHas()) {
                        Pair<String, String> segmentPair = createHasSegmentation(hasValue, attributeName);

                        if (segmentPair.getValue().isBlank() && !hasKey(segmentPair.getKey(), hasStates)){
                            return false;
                        }
                        else if (!hasKeyAndValue(segmentPair, hasStates)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean evaluateOrCondition(List<AttributeModel.Condition.Or> orList, HashMap<String, String> hasStates, String attributeName) {
        if (orList != null && !orList.isEmpty()){
            for (AttributeModel.Condition.Or or : orList) {
                if (or.getHas() != null && !or.getHas().isEmpty()){
                    for (String hasValue : or.getHas()) {
                        Pair<String, String> segmentPair = createHasSegmentation(hasValue, attributeName);

                        if (segmentPair.getValue().isBlank() && hasKey(segmentPair.getKey(), hasStates)) {
                            return true;
                        }
                        else if (hasKeyAndValue(segmentPair, hasStates)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    private boolean hasKey(String key, HashMap<String, String> hasStates){
        return hasStates.containsKey(key);
    }

    private boolean hasKeyAndValue(Pair<String, String> pair, HashMap<String, String> hasStates){
        return hasStates.containsKey(pair.getKey()) && hasStates.get(pair.getKey()).equals(pair.getValue());
    }

    private Pair<String, String> createHasSegmentation(String hasElement, String attributeName){
        String[] hasSegments = hasElement.split("\\.");
        Pair<String, String> resultPair = new Pair<>();
        resultPair.setValue("");

        if (hasSegments.length == 1){
            resultPair.setKey(hasSegments[0]);
        }
        else if (hasSegments.length == 2){
            resultPair.setKey(hasSegments[0]);
            resultPair.setValue(hasSegments[1]);
        }
        else
            throw new QueryException(String.format("Invalid number of segments for '%s' in attribute '%s'", hasElement, attributeName));

        return resultPair;
    }
}
