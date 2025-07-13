package models;

import java.util.Collection;

public interface Cache<K, V> {
    V get(K key);
    void put(K key, V value);
    void remove(Collection<K> key); // if user wants to explicitly remove any specific key(s)
}
