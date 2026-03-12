package de.projectmodding.gui.manager;

import de.projectmodding.core.enums.DataType;
import de.projectmodding.gui.dataTypeComponent.IDataTypeComponent;

import java.awt.*;
import java.util.HashMap;

public class DatatypeComponentManager {

    private final HashMap<DataType, IDataTypeComponent> components = new HashMap<>();

    public DatatypeComponentManager(){}

    public void registerComponent(DataType key, IDataTypeComponent component) {
        if (!components.containsKey(key)){
            components.put(key, component);
        }
    }

    public IDataTypeComponent getComponent(DataType key){
        if (components.containsKey(key))
            return components.get(key);
        else
            throw new IllegalArgumentException("No component with key " + key.toString());
    }
}
