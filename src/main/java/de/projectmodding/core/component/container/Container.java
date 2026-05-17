package de.projectmodding.core.component.container;

import de.projectmodding.core.exception.ContainerException;

import java.util.HashMap;

public class Container {
    private final HashMap<Class<?>, HashMap<String, Object>> objMap = new HashMap<>();

    public Container(){}

    public <T> void register(Class<T> clazz, Object object){
        if (!objMap.containsKey(clazz)) {
            HashMap<String, Object> innerMap = new HashMap<>();
            innerMap.put(object.getClass().getSimpleName(), object);
            objMap.put(clazz, innerMap);
        }
        else
            throw new ContainerException(String.format("Container class '%s' does exist.", clazz.getSimpleName()));
    }

    public <T> void register(Class<T> clazz, String key, Object object){
        if (objMap.containsKey(clazz)) {
            HashMap<String, Object> innerMap = objMap.get(clazz);

            if (!innerMap.containsKey(key)) {
                innerMap.put(key, object);
            }
            else
                throw new ContainerException(String.format("Container class '%s' with key '%s' is existing.", clazz.getSimpleName(), key));
        }
        else{
            HashMap<String, Object> innerMap = new HashMap<>();
            innerMap.put(key, object);
            objMap.put(clazz, innerMap);
        }
    }

    public <T> T resolve(Class<T> keyClazz){
        if (objMap.containsKey(keyClazz)){
            HashMap<String, Object> innerMap = objMap.get(keyClazz);

            if (innerMap.size() == 1){
                Object obj = innerMap.get(keyClazz.getSimpleName());

                if (obj.getClass().isAssignableFrom(keyClazz))
                    return keyClazz.cast(obj);
                else
                    throw new ContainerException(String.format("Class '%s' cannot be assigned to '%s'.", keyClazz.getSimpleName(), obj.getClass().getSimpleName()));
            }
            else{
                throw new ContainerException(String.format("Container class '%s' is not a single element.", keyClazz.getSimpleName()));
            }
        }
        else
            throw new ContainerException(String.format("Container class '%s' does not exist.", keyClazz.getSimpleName()));
    }

    public <T> T resolve(Class<T> keyClazz, String key){
        if (objMap.containsKey(keyClazz)) {
            HashMap<String, Object> innerMap = objMap.get(keyClazz);

            if (innerMap.containsKey(key))
                return keyClazz.cast(innerMap.get(key));
            else
                throw new ContainerException(String.format("Container class '%s' with key '%s' does not exist.", keyClazz.getSimpleName(), key));
        }
        else
            throw new ContainerException(String.format("Container class '%s' does not exist.", keyClazz.getSimpleName()));
    }
}
