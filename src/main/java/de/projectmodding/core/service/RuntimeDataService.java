package de.projectmodding.core.service;

import de.projectmodding.core.exception.RuntimeDataServiceException;

import java.util.HashMap;

public class RuntimeDataService {
    private final HashMap<Class<?>, Object> dataMap = new HashMap<>();
    public RuntimeDataService() {}

    public <T> void save(T data){
        if (!dataMap.containsKey(data.getClass())) {
            dataMap.put(data.getClass(), data);
        }
    }

    public <T> T getByType(Class<T> dataClass) {
        if (dataMap.containsKey(dataClass)) {
            return dataClass.cast(dataMap.get(dataClass));
        }
        else
            throw new RuntimeDataServiceException(String.format("No DataClass found for class %s", dataClass.getName()));
    }

    public <T> void remove(Class<T> dataClass) {
        if (dataMap.containsKey(dataClass)) {
            dataMap.remove(dataClass);
        }
    }
}
