package de.projectmodding.gui.generator;

import de.projectmodding.core.component.event.event.ReloadAttributesEvent;
import de.projectmodding.core.component.event.system.EventSystem;
import de.projectmodding.core.constant.definition.AttributeConstants;
import de.projectmodding.core.enums.DataType;
import de.projectmodding.core.enums.ParameterAction;
import de.projectmodding.core.exception.ScriptPanelAttributeGeneratorException;
import de.projectmodding.core.model.AttributeModel;
import de.projectmodding.core.model.definition.FileDefinitionModel;
import de.projectmodding.core.model.intern.Pair;
import de.projectmodding.core.model.mod.files.data.ScriptBlock;
import de.projectmodding.gui.dataTypeComponent.AbstractComponentPanel;
import de.projectmodding.gui.dataTypeComponent.IDataTypeComponent;
import de.projectmodding.gui.manager.DatatypeComponentManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScriptPanelRequiredAttributeGenerator {

    private static ScriptPanelRequiredAttributeGenerator instance;
    private final List<AbstractComponentPanel> clearingList = new ArrayList<>();

    public static ScriptPanelRequiredAttributeGenerator getInstance()
    {
        if (instance == null){
            instance = new ScriptPanelRequiredAttributeGenerator();
        }
        return instance;
    }

    public void generate(JPanel viewPanel, FileDefinitionModel definitionModel, ScriptBlock block, DatatypeComponentManager componentManager){
        cleanUp();

        if (block.getAttributes().isEmpty())
            generateRequiredAttributes(definitionModel, block);

        generateGUI(viewPanel, definitionModel, block, componentManager);
    }

    private void cleanUp(){
        clearingList.forEach(AbstractComponentPanel::clear);
        clearingList.clear();
    }

    private void generateGUI(JPanel viewPanel, FileDefinitionModel definitionModel, ScriptBlock block, DatatypeComponentManager componentManager){
        block.getAttributes().forEach(attribute -> {
            if (attribute.getRequired() && attribute.getAction() != null){
                AbstractComponentPanel component = createExistingAttributePanel(attribute, componentManager);
                if (attribute.getAction().equals(ParameterAction.Load_Script_Types)){
                    component.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            EventSystem.getInstance().fireEvent(new ReloadAttributesEvent(null));
                        }
                    });


                }
                else if (attribute.getAction().equals(ParameterAction.Load_Item_Types)){
                    //Todo
                }

                if (evaluateCondition(attribute.getCondition(), block, attribute.getKeyName())) {
                    viewPanel.add(component.getPanel());
                    component.showComponent();
                    clearingList.add(component);
                }
            }
        });
    }

    private boolean evaluateCondition(AttributeModel.Condition condition, ScriptBlock block, String attributeName) {
        if (condition == null)
            return true;
        else {
            return evaluateAndCondition(condition.getAnd(), block, attributeName) && evaluateOrCondition(condition.getOr(), block, attributeName);
        }
    }

    private boolean evaluateAndCondition(List<AttributeModel.Condition.And> andList, ScriptBlock block, String attributeName) {
        if (andList != null && !andList.isEmpty()) {
            for (AttributeModel.Condition.And and : andList) {
                if (and.getHas() != null && !and.getHas().isEmpty()){
                    for (String hasValue : and.getHas()) {
                        Pair<String, String> segmentPair = createHasSegmentation(hasValue, attributeName);

                        if (segmentPair.getValue().isBlank() && !scriptBlockContainsKey(segmentPair.getKey(), block)){
                            return false;
                        }
                        else if (!scriptBlockContainsKeyAndValue(segmentPair, block)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean evaluateOrCondition(List<AttributeModel.Condition.Or> orList, ScriptBlock block, String attributeName) {
        if (orList != null && !orList.isEmpty()){
            for (AttributeModel.Condition.Or or : orList) {
                if (or.getHas() != null && !or.getHas().isEmpty()){
                    for (String hasValue : or.getHas()) {
                        Pair<String, String> segmentPair = createHasSegmentation(hasValue, attributeName);

                        if (segmentPair.getValue().isBlank() && scriptBlockContainsKey(segmentPair.getKey(), block)) {
                            return true;
                        }
                        else if (scriptBlockContainsKeyAndValue(segmentPair, block)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    private boolean scriptBlockContainsKey(String key, ScriptBlock block){
        return block.getAttributes().stream().anyMatch(attribute -> attribute.getCustomType().equals(key));
    }

    private boolean scriptBlockContainsKeyAndValue(Pair<String, String> pair, ScriptBlock block){
        return block.getAttributes().stream().anyMatch(attribute -> attribute.getCustomType().equals(pair.getKey()) &&
                attribute.getValue().equals(pair.getValue()));
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
            throw new ScriptPanelAttributeGeneratorException(String.format("Invalid number of segments for '%s' in attribute '%s'", hasElement, attributeName));

        return resultPair;
    }

    private void generateRequiredAttributes(FileDefinitionModel definitionModel, ScriptBlock block){
        definitionModel.getAttributes().forEach(attribute -> {
            if (attribute.getRequired()){
                AttributeModel copiedAttribute = AttributeModel.copy(attribute);
                setUpDataType(copiedAttribute, definitionModel);
                block.getAttributes().add(copiedAttribute);
            }
        });
    }

    private void setUpDataType(AttributeModel attributeModel, FileDefinitionModel definitionModel){
        if (Objects.requireNonNull(attributeModel.getDataType()) == DataType.Custom) {
            attributeModel.setInternalData(createInternArray(definitionModel.getCustomDataTypeMap().get(attributeModel.getCustomType())));
            attributeModel.setValue("");
        } else {
            attributeModel.setValue("");
        }
    }

    private String createInternArray(List<String> dataArray){
        if (dataArray != null && !dataArray.isEmpty()){
            StringBuilder builder = new StringBuilder();

            dataArray.forEach(data -> {
                builder.append(data.concat(AttributeConstants.GLOBAL_DELIMITER));
            });
            return builder.toString();
        }
        else
            return "";
    }

    private AbstractComponentPanel createExistingAttributePanel(AttributeModel attributeModel, DatatypeComponentManager componentManager){
        IDataTypeComponent component = componentManager.getComponent(attributeModel.getDataType());
        component.setAttribute(attributeModel);
        return (AbstractComponentPanel) component;
    }
}
