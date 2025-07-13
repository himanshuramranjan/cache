package models;

public interface EvictionPolicy<K> {
    void recordKeyAccess(K key);
    K evictKey();
    void removeKey(K key);
}
