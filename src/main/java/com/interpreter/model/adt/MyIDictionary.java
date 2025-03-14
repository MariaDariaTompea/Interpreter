package com.interpreter.model.adt;

import com.interpreter.exceptions.KeyNotFoundException;

import java.util.Map;
import java.util.Set;

public interface MyIDictionary <K, V>{
    void insert(K key, V value);
    V getValue(K key) throws KeyNotFoundException;
    void remove(K key) throws KeyNotFoundException;
    boolean contains(K key);
    Set<K> getKeys();
    Map<K, V> getMap();
    MyIDictionary<K, V> clone();
}
