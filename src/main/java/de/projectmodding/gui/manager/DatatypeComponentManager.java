package de.projectmodding.gui.manager;

import de.projectmodding.core.enums.DataType;
import de.projectmodding.gui.dataTypeComponent.AbstractComponentPanel;
import de.projectmodding.gui.dataTypeComponent.IDataTypeComponent;
import de.projectmodding.gui.dataTypeComponent.factory.ComponentFactory;
import java.util.ArrayList;
import java.util.HashMap;

public class DatatypeComponentManager {

    private final ComponentFactory componentFactory = new ComponentFactory();
    private static final int BASE_POOL_SIZE = 64;
    private final HashMap<DataType, Class<? extends AbstractComponentPanel>> componentFactories = new HashMap<>();
    private final HashMap<DataType, ArrayList<AbstractComponentPanel>> componentPools = new HashMap<>();

    public DatatypeComponentManager() {
    }

    public <T extends AbstractComponentPanel> void registerComponent(DataType key, Class<T> component) {
        if (!componentFactories.containsKey(key)) {
            componentFactories.put(key, component);
            componentPools.put(key, allocatePool(component));

        }
    }

    private  <T extends AbstractComponentPanel> ArrayList<AbstractComponentPanel> allocatePool(Class<T> component){
        ArrayList<AbstractComponentPanel> pool = new ArrayList<>(BASE_POOL_SIZE);
        for (int i = 0; i < BASE_POOL_SIZE; i++) {
            pool.add((AbstractComponentPanel) componentFactory.createComponent(component));
        }
        return pool;
    }

    public IDataTypeComponent getComponent(DataType key) {
        if (componentFactories.containsKey(key)) {
            AbstractComponentPanel component = findAndGetInactiveComponentInPool(key);

            if (component == null) {
                component = (AbstractComponentPanel) componentFactory.createComponent(componentFactories.get(key));
                componentPools.get(key).add(component);
            }

            return component;
        } else
            throw new IllegalArgumentException("No component with key " + key.toString());
    }

    private AbstractComponentPanel findAndGetInactiveComponentInPool(DataType key) {
        AbstractComponentPanel resultComponent = null;

        for (AbstractComponentPanel component : componentPools.get(key)) {
            if (!component.isActive()) {
                resultComponent = component;
                break;
            }
        }

        return resultComponent;
    }
}
