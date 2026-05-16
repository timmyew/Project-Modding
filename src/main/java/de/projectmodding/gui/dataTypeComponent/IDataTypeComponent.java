package de.projectmodding.gui.dataTypeComponent;

import de.projectmodding.core.model.AttributeModel;

import javax.swing.*;
import java.awt.event.ActionListener;

public interface IDataTypeComponent {
    void setAttribute(AttributeModel attribute);
    AttributeModel getAttribute();
    Boolean isDataValid();
    JPanel getPanel();
    void addActionListener(ActionListener actionListener);
    void removeActionListener();
}
