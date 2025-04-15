package models;

import java.util.HashMap;
import java.util.Map;

public class Storage<K, V> {
    private final Map<K, V> map;

    public Storage() {
        this.map = new HashMap<>();
    }

    public V get(K key) {
        return map.get(key);
    }

    public void put(K key, V value) {
        map.put(key, value);
    }

    public void remove(K key) {
        map.remove(key);
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public int size() {
        return map.size();
    }
}
