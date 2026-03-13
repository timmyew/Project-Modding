package de.projectmodding.gui.dataTypeComponent.factory;

import de.projectmodding.gui.dataTypeComponent.*;

public class ComponentFactory implements IComponentFactory{
    @Override
    public <T extends IDataTypeComponent> IDataTypeComponent createComponent(Class<T> componentClass) {
        if (componentClass == ArrayComponent.class)
            return new ArrayComponent();
        else if (componentClass == BooleanComponent.class)
            return new BooleanComponent();
        else if (componentClass == CustomComponent.class)
            return new CustomComponent();
        else if (componentClass == FileComponent.class)
            return new FileComponent();
        else if (componentClass == IntegerComponent.class)
            return new IntegerComponent();
        else if (componentClass == StringComponent.class)
            return new StringComponent();
        else  if (componentClass == FloatComponent.class)
            return new FloatComponent();
        else
            throw new IllegalArgumentException("Unknown component class " + componentClass.getName());
    }
}
