package de.projectmodding.gui.dataTypeComponent;

import de.projectmodding.core.model.AttributeModel;

import javax.swing.*;

public interface IDataTypeComponent {
    void setAttribute(AttributeModel attribute);
    AttributeModel getAttribute();
    Boolean isDataValid();
    JPanel getPanel();
}
