package org.tech.vineyard.hash;

import java.util.Map.Entry;

public class Node<K, V> implements Entry<K, V> {
    private final K key;
    private V value;
    private Node<K, V> next;

    public Node(final K key, final V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    public Node<K, V> next() {
        return next;
    }

    public void next(final Node<K, V> next) {
        this.next = next;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        this.value = value;
        return value;
    }

    @Override
    public boolean equals(Object object) {
        final Node<K, V> other = (Node<K, V>) object;
        return key.equals(other.getKey()) && value.equals(other.getValue());
    }
}
