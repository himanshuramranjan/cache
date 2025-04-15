package models;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeCache<K, V> implements Cache<K, V> {

    private final Storage<K, V> storage;
    private final EvictionPolicy<K> evictionPolicy;
    private final int capacity;
    private final Lock lock = new ReentrantLock();

    public ThreadSafeCache(int capacity, EvictionPolicy<K> evictionPolicy) {
        this.capacity = capacity;
        this.evictionPolicy = evictionPolicy;
        this.storage = new Storage<>();
    }

    @Override
    public V get(K key) {
        lock.lock();
        try {
            if (!storage.containsKey(key)) return null;
            evictionPolicy.keyAccessed(key);
            return storage.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void put(K key, V value) {
        lock.lock();
        try {
            if (storage.size() >= capacity && !storage.containsKey(key)) {
                K evictedKey = evictionPolicy.evictKey();
                if (evictedKey != null) {
                    storage.remove(evictedKey);
                }
            }
            storage.put(key, value);
            evictionPolicy.keyAccessed(key);
        } finally {
            lock.unlock();
        }
    }
}
