package de.projectmodding.gui.dataTypeComponent.factory;

import de.projectmodding.gui.dataTypeComponent.IDataTypeComponent;

public interface IComponentFactory {
    <T extends IDataTypeComponent> IDataTypeComponent createComponent(Class<T> componentClass);
}
