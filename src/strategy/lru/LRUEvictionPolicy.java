package strategy.lru;

import models.EvictionPolicy;

import java.util.HashMap;
import java.util.Map;

public class LRUEvictionPolicy<K> implements EvictionPolicy<K> {
    private final Map<K, DoublyLinkedNode<K>> keyToNodeMap;
    private final DoublyLinkedList<K> lruList;

    public LRUEvictionPolicy() {
        this.keyToNodeMap = new HashMap<>();
        this.lruList = new DoublyLinkedList<>();
    }
    @Override
    public void recordKeyAccess(K key) {
        if (keyToNodeMap.containsKey(key)) {
            DoublyLinkedNode<K> node = keyToNodeMap.get(key);
            lruList.remove(node);
            lruList.addFirst(node);
        } else {
            DoublyLinkedNode<K> newNode = new DoublyLinkedNode<>(key);
            lruList.addFirst(newNode);
            keyToNodeMap.put(key, newNode);
        }
    }

    @Override
    public K evictKey() {
        DoublyLinkedNode<K> node = lruList.removeLast();
        if (node == null) return null;
        keyToNodeMap.remove(node.key);
        System.out.println("Key evicted from the cache " + node.key.toString());
        return node.key;
    }

    @Override
    public void removeKey(K key) {
        DoublyLinkedNode<K> node = keyToNodeMap.remove(key);
        if (node != null) {
            lruList.remove(node);
        }
    }
}
