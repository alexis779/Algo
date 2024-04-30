package org.tech.vineyard.hash;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


/**
 * HashMap with chaining.
 * Use a linked list to handle collisions.
 */
public class ChainingHashMap<K, V> implements Map<K, V> {

    private final int capacity;
    private final Node<K, V>[] table;
    private int size;

    public ChainingHashMap(final int capacity) {
        this.capacity = capacity;
        table = new Node[capacity];
        size = 0;
    }

    @Override
    public boolean equals(Object object) {
        final ChainingHashMap<K, V> other = (ChainingHashMap<K, V>) object;
        return entrySet().equals(other.entrySet());
    }


    @Override
    public V get(Object object) {
        final K key = (K) object;

        final int hash = hash(key);
        Node<K, V> node = table[hash];
        if (node == null) {
            return null;
        }

        while (node != null && !node.getKey().equals(key)) {
            node = node.next();
        }
        return node == null ? null : node.getValue();
    }

    @Override
    public V put(K key, V value) {
        final int hash = hash(key);

        Node<K, V> previous = null;
        Node<K, V> node = table[hash];
        while (node != null && !node.getKey().equals(key)) {
            previous = node;
            node = node.next();
        }

        if (node != null) {
            if (node.getValue().equals(value)) {
                return value;
            }

            // update the value associated to this key
            node.setValue(value);
            return value;
        }

        final Node<K,V> node2 = new Node<>(key, value);
        size++;

        if (previous == null) {
            // assert table[hash] == null
            table[hash] = node2;
            return value;
        }

        // previous is the last element in the linked list
        // this will append the new node at the end
        previous.next(node2);
        return value;
    }

    @Override
    public V remove(Object object) {
        final K key = (K) object;

        final int hash = hash(key);

        Node<K, V> previous = null;
        Node<K, V> node = table[hash];
        while (node != null && !node.getKey().equals(key)) {
            previous = node;
            node = node.next();
        }

        if (node == null) {
            return null;
        }

        size--;
        if (previous == null) {
            table[hash] = node.next();
            return node.getValue();
        }

        previous.next(node.next());
        return node.getValue();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new TableEntrySet<K, V>(this);
    }

    @Override
    public Set<K> keySet() {
        return new TableKeySet<K, V>(this);
    }

    @Override
    public Collection<V> values() {
        return new TableValueSet<K, V>(this);
    }

    private int hash(K key) {
        return (key.hashCode() % capacity + capacity) % capacity;
    }


    @Override
    public boolean isEmpty() {
        return size() == 0;
    }


    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }


    @Override
    public boolean containsValue(Object value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsValue'");
    }


    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putAll'");
    }


    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    public Node<K, V>[] table() {
        return table;
    }
}

