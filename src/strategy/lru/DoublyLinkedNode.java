package strategy.lru;

public class DoublyLinkedNode<K> {
    public K key;
    DoublyLinkedNode<K> prev, next;

    public DoublyLinkedNode(K key) {
        this.key = key;
    }
}
