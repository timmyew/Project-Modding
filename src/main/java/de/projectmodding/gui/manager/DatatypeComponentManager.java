package de.projectmodding.gui.manager;

import de.projectmodding.core.enums.DataType;
import de.projectmodding.gui.dataTypeComponent.IDataTypeComponent;
import de.projectmodding.gui.dataTypeComponent.factory.ComponentFactory;

import java.awt.*;
import java.util.HashMap;

public class DatatypeComponentManager {

    private final ComponentFactory componentFactory = new ComponentFactory();
    private final HashMap<DataType, Class<? extends IDataTypeComponent>> components = new HashMap<>();

    public DatatypeComponentManager(){}

    public <T extends IDataTypeComponent> void registerComponent(DataType key, Class<T> component) {
        if (!components.containsKey(key)){
            components.put(key, component);
        }
    }

    public IDataTypeComponent getComponent(DataType key){
        if (components.containsKey(key))
            return componentFactory.createComponent(components.get(key));
        else
            throw new IllegalArgumentException("No component with key " + key.toString());
    }
}
