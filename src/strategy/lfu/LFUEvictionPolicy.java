package strategy.lfu;

import models.EvictionPolicy;
import strategy.lru.DoublyLinkedList;
import strategy.lru.DoublyLinkedNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUEvictionPolicy<K> implements EvictionPolicy<K> {
    private final Map<K, Integer> keyFrequency = new HashMap<>();
    private final Map<Integer, LinkedHashSet<K>> freqMap = new HashMap<>();
    private int minFreq = 0;

    @Override
    public void keyAccessed(K key) {
        int freq = keyFrequency.getOrDefault(key, 0);
        keyFrequency.put(key, freq + 1);

        freqMap.computeIfAbsent(freq + 1, k -> new LinkedHashSet<>()).add(key);
        if (freq > 0) {
            freqMap.get(freq).remove(key);
            if (freqMap.get(freq).isEmpty()) {
                freqMap.remove(freq);
                if (minFreq == freq) minFreq++;
            }
        } else {
            minFreq = 1;
        }
    }

    @Override
    public K evictKey() {
        if (!freqMap.containsKey(minFreq)) return null;
        Iterator<K> it = freqMap.get(minFreq).iterator();
        if (!it.hasNext()) return null;
        K key = it.next();
        it.remove();
        keyFrequency.remove(key);
        if (freqMap.get(minFreq).isEmpty()) {
            freqMap.remove(minFreq);
        }
        System.out.println("Key evicted from the cache " + key.toString());
        return key;
    }

    @Override
    public void removeKey(K key) {
        int freq = keyFrequency.getOrDefault(key, 0);
        if (freq > 0 && freqMap.containsKey(freq)) {
            freqMap.get(freq).remove(key);
            if (freqMap.get(freq).isEmpty()) {
                freqMap.remove(freq);
                if (minFreq == freq) minFreq++;
            }
        }
        keyFrequency.remove(key);
    }
}

