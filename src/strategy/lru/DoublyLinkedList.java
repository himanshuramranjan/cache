package strategy.lru;

public class DoublyLinkedList<K> {
    private final DoublyLinkedNode<K> head;
    private final DoublyLinkedNode<K> tail;

    public DoublyLinkedList() {
        head = new DoublyLinkedNode<>(null);
        tail = new DoublyLinkedNode<>(null);
        head.next = tail;
        tail.prev = head;
    }

    public void addFirst(DoublyLinkedNode<K> node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    public void remove(DoublyLinkedNode<K> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public DoublyLinkedNode<K> removeLast() {
        if(tail.prev == head) return null;
        DoublyLinkedNode<K> last = tail.prev;
        remove(last);
        return last;
    }

    public boolean isEmpty() {
        return head.next == null;
    }

    public DoublyLinkedNode<K> getFirstNode() {
        return head.next != tail ? head.next : null;
    }
}
