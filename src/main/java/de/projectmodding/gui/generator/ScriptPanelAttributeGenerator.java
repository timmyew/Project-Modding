package de.projectmodding.gui.generator;

import de.projectmodding.core.model.AttributeModel;
import de.projectmodding.core.model.definition.FileDefinitionModel;
import de.projectmodding.core.model.mod.files.data.ScriptBlock;
import de.projectmodding.gui.dataTypeComponent.IDataTypeComponent;
import de.projectmodding.gui.manager.DatatypeComponentManager;

import javax.swing.*;

public class ScriptPanelAttributeGenerator {
    private ScriptPanelAttributeGenerator(){}

    public static void generateRequired(JPanel viewPanel, FileDefinitionModel definitionModel, ScriptBlock block, DatatypeComponentManager componentManager) {
        definitionModel.getAttributes().forEach(attribute -> {
            if (attribute.getRequired() && attribute.getCondition() == null){
                createAttribute(attribute, block, componentManager);
            }
        });
    }

    private static void createAttribute(AttributeModel attributeModel, ScriptBlock block, DatatypeComponentManager componentManager) {
        IDataTypeComponent component = componentManager.getComponent(attributeModel.getDataType());
        AttributeModel attribute = AttributeModel.copy(attributeModel);
        block.getAttributes().add(attribute);
        component.setAttribute(attribute);
    }
}
