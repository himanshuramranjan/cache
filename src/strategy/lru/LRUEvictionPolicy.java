package strategy.lru;

import models.EvictionPolicy;

import java.util.HashMap;
import java.util.Map;

public class LRUEvictionPolicy<K> implements EvictionPolicy<K> {
    private final Map<K, DoublyLinkedNode<K>> map;
    private final DoublyLinkedList<K> list;

    public LRUEvictionPolicy() {
        this.map = new HashMap<>();
        this.list = new DoublyLinkedList<>();
    }
    @Override
    public void recordKeyAccess(K key) {
        if (map.containsKey(key)) {
            DoublyLinkedNode<K> node = map.get(key);
            list.remove(node);
            list.addFirst(node);
        } else {
            DoublyLinkedNode<K> newNode = new DoublyLinkedNode<>(key);
            list.addFirst(newNode);
            map.put(key, newNode);
        }
    }

    @Override
    public K evictKey() {
        DoublyLinkedNode<K> node = list.removeLast();
        if (node == null) return null;
        map.remove(node.key);
        System.out.println("Key evicted from the cache " + node.key.toString());
        return node.key;
    }

    @Override
    public void removeKey(K key) {
        DoublyLinkedNode<K> node = map.remove(key);
        if (node != null) {
            list.remove(node);
        }
    }
}
