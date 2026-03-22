package de.projectmodding.gui.generator;

import de.projectmodding.core.component.event.Event;
import de.projectmodding.core.component.event.Listener;
import de.projectmodding.core.constant.definition.AttributeConstants;
import de.projectmodding.core.enums.ParameterAction;
import de.projectmodding.core.exception.ScriptPanelAttributeGeneratorException;
import de.projectmodding.core.model.AttributeModel;
import de.projectmodding.core.model.definition.FileDefinitionModel;
import de.projectmodding.core.model.event.StateChangeEvent;
import de.projectmodding.core.model.intern.Pair;
import de.projectmodding.core.model.mod.files.data.ScriptBlock;
import de.projectmodding.gui.dataTypeComponent.IDataTypeComponent;
import de.projectmodding.gui.manager.DatatypeComponentManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScriptPanelAttributeGenerator implements Listener {
    private String currentAttributeName;

    private static ScriptPanelAttributeGenerator instance;
    private HashMap<String, String> scriptStateMap;
    private final List<IDataTypeComponent> componentCollection = new ArrayList<>();
    private ScriptPanelAttributeGenerator(){}

    public static ScriptPanelAttributeGenerator getInstance()
    {
        if (instance == null){
            instance = new ScriptPanelAttributeGenerator();
        }
        return instance;
    }

    public void generateRequired(JPanel viewPanel, FileDefinitionModel definitionModel, ScriptBlock block, DatatypeComponentManager componentManager) {
        componentCollection.clear();

        if (scriptStateMap == null) {
            generateNewRequired(viewPanel, definitionModel, block, componentManager);
        }
        else{
            generateExistingRequired(viewPanel, definitionModel, block, componentManager);
        }

        buildPanel(viewPanel);
    }

    private void buildPanel(JPanel viewPanel){
        viewPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        componentCollection.forEach(component -> {
            viewPanel.add(component.getPanel());
        });
    }

    private void generateExistingRequired(JPanel viewPanel, FileDefinitionModel definitionModel, ScriptBlock block, DatatypeComponentManager componentManager){
        block.getAttributes().forEach(attribute -> {
            currentAttributeName = attribute.getKeyName();

            if (attribute.getRequired() && attribute.getAction() != null){
                IDataTypeComponent component = null;
                if (attribute.getAction().equals(ParameterAction.Load_Script_Types)){
                    component = createExistingAttributePanel(attribute, componentManager);
                }
                else if (attribute.getAction().equals(ParameterAction.Load_Script_Block_Types) && evaluateCondition(attribute.getCondition())){
                    component = createExistingAttributePanel(attribute, componentManager);
                }

                if (component != null)
                    componentCollection.add(component);
            }
        });
    }

    private void generateNewRequired(JPanel viewPanel, FileDefinitionModel definitionModel, ScriptBlock block, DatatypeComponentManager componentManager){
        definitionModel.getAttributes().forEach(attribute -> {
            if (attribute.getRequired() && attribute.getAction() != null && attribute.getAction().equals(ParameterAction.Load_Script_Types)){
                IDataTypeComponent component = createNewAttributePanel(attribute, block, componentManager);

                AttributeModel componentAttribute = component.getAttribute();
                componentAttribute.setValue(createInternArray(definitionModel.getCustomDataTypeMap().get(attribute.getCustomType())));

                componentCollection.add(component);
            }
        });
    }

    private boolean evaluateCondition(AttributeModel.Condition condition) {
        if (condition == null)
            return true;
        else {
            return evaluateAndCondition(condition.getAnd()) && evaluateOrCondition(condition.getOr());
        }
    }

    private boolean evaluateAndCondition(List<AttributeModel.Condition.And> andList) {
        if (andList != null && !andList.isEmpty()) {
            for (AttributeModel.Condition.And and : andList) {
                if (and.getHas() != null && !and.getHas().isEmpty()){
                    for (String hasValue : and.getHas()) {
                        Pair<String, String> segmentPair = createHasSegmentation(hasValue);

                        if (segmentPair.getValue().isBlank() && !scriptStateMap.containsKey(segmentPair.getKey())){
                            return false;
                        }
                        else if (!hasScriptStates(segmentPair)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean evaluateOrCondition(List<AttributeModel.Condition.Or> orList) {
        if (orList != null && !orList.isEmpty()){
            for (AttributeModel.Condition.Or or : orList) {
                if (or.getHas() != null && !or.getHas().isEmpty()){
                    for (String hasValue : or.getHas()) {
                        Pair<String, String> segmentPair = createHasSegmentation(hasValue);

                        if (segmentPair.getValue().isBlank() && scriptStateMap.containsKey(segmentPair.getKey())) {
                            return true;
                        }
                        else if (hasScriptStates(segmentPair)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    private Pair<String, String> createHasSegmentation(String hasElement){
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
            throw new ScriptPanelAttributeGeneratorException(String.format("Invalid number of segments for '%s' in attribute '%s'", hasElement, currentAttributeName));

        return resultPair;
    }

    private boolean hasScriptStates(Pair<String, String> hasSegmentPair){
        if (scriptStateMap == null)
            return false;
        else if (scriptStateMap.isEmpty())
            return false;
        else {
            return scriptStateMap.containsKey(hasSegmentPair.getKey()) && scriptStateMap.get(hasSegmentPair.getKey()).equals(hasSegmentPair.getValue());
        }
    }

    private IDataTypeComponent createExistingAttributePanel(AttributeModel attributeModel, DatatypeComponentManager componentManager){
        IDataTypeComponent component = componentManager.getComponent(attributeModel.getDataType());
        component.setAttribute(attributeModel);
        return component;
    }

    private IDataTypeComponent createNewAttributePanel(AttributeModel attributeModel, ScriptBlock block, DatatypeComponentManager componentManager) {
        IDataTypeComponent component = componentManager.getComponent(attributeModel.getDataType());
        AttributeModel attribute = AttributeModel.copy(attributeModel);
        block.getAttributes().add(attribute);
        component.setAttribute(attribute);
        return component;
    }

    private String createInternArray(List<String> dataArray){
        StringBuilder builder = new StringBuilder();

        dataArray.forEach(data -> {
            builder.append(data.concat(AttributeConstants.GLOBAL_DELIMITER));
        });
        return builder.toString();
    }

    @Override
    public <T> void onEvent(Event<T> event) {
        if (event instanceof StateChangeEvent stateChangeEvent) {
            scriptStateMap = stateChangeEvent.getData();
        }
    }
}
