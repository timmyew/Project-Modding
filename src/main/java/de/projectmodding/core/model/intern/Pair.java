package de.projectmodding.core.model.intern;

import lombok.Data;

@Data
public class Pair <K,V> {
    private K key;
    private V value;
}
