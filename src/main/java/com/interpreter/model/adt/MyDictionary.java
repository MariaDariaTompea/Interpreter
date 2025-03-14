package com.interpreter.model.adt;

import com.interpreter.exceptions.KeyNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K, V> implements MyIDictionary<K, V>{
    private final Map<K, V> map;

    public MyDictionary() {
        this.map = new HashMap<>();
    }

    @Override
    public void insert(K key, V value) {
        this.map.put(key, value);
    }

    @Override
    public V getValue(K key) throws KeyNotFoundException {
        if (!this.map.containsKey(key)) {
            throw new KeyNotFoundException("Key doesn't exist");
        }
        return this.map.get(key);
    }

    @Override
    public void remove(K key) throws KeyNotFoundException {
        if (!this.map.containsKey(key)) {
            throw new KeyNotFoundException("Key doesn't exist");
        }
        this.map.remove(key);
    }

    @Override
    public boolean contains(K key) {
        return map.containsKey(key);
    }

    public Map<K, V> getMap() {
        return map;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        for(K key: this.map.keySet()){
            str.append(key).append(" -> ").append(this.map.get(key)).append("\n");
        }
        return "MyDictionary contains:\n" + str;
    }

    @Override
    public Set<K> getKeys() {
        return this.map.keySet();
    }

    @Override
    public MyIDictionary<K, V> clone() {
        MyIDictionary<K, V> clone = new MyDictionary<>();
        for (K key : this.map.keySet()) {
            clone.insert(key, this.map.get(key));
        }
        return clone;
    }
}
