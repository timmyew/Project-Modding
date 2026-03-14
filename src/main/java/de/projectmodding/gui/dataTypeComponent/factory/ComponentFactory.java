package de.projectmodding.gui.dataTypeComponent.factory;

import de.projectmodding.gui.dataTypeComponent.*;

public class ComponentFactory implements IComponentFactory{
    @Override
    public <T extends IDataTypeComponent> IDataTypeComponent createComponent(Class<T> componentClass) {

        try{
            return componentClass.getDeclaredConstructor().newInstance();
        }
        catch(Exception exception){
            throw new IllegalArgumentException(String.format("Unknown component class %s \n %s", componentClass.getName(), exception.getMessage()));
        }
    }
}
