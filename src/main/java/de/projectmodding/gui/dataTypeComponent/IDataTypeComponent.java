package de.projectmodding.gui.dataTypeComponent;

import de.projectmodding.core.model.AttributeModel;

public interface IDataTypeComponent {
    void setAttribute(AttributeModel attribute, boolean isRequired);
    Boolean isDataValid();
}
