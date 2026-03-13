package de.projectmodding.gui.dataTypeComponent;

import de.projectmodding.core.model.AttributeModel;

import javax.swing.*;

public abstract class AbstractComponentPanel extends JPanel implements IDataTypeComponent {
    protected AttributeModel attribute;

    public void setAttribute(AttributeModel attributeModel){
        this.attribute = attributeModel;
        createUIComponents();
    }

    protected abstract void createUIComponents();

    private String getDisplayName(){
        return attribute.getDisplayName() != null && !attribute.getDisplayName().isBlank() ? attribute.getDisplayName() : attribute.getKeyName();
    }

    protected JLabel getDisplayNameLabel(){
        return new JLabel(getDisplayName());
    }
}
