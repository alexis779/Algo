package org.tech.vineyard.hash;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class TableEntryIterator<K, V> implements Iterator<Map.Entry<K,V>> {

    private final ChainingHashMap<K,V> hashMap;

    private int binId;
    private Node<K, V> current;
    public TableEntryIterator(final ChainingHashMap<K,V> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public boolean hasNext() {
        final Node<K, V>[] table = hashMap.table();
        if (binId == table.length) {
            return false;
        }

        if (current == null) {
            findNext();
        }
        return current != null;
    }

    @Override
    public Entry<K, V> next() {
        if (current == null) {
            findNext();
        }
        final Entry<K, V> entry = current;
        current = current.next();
        if (current == null) {
            binId++;
        }
        return entry;
    }

    private void findNext() {
        final Node<K, V>[] table = hashMap.table();
        while (binId < table.length && table[binId] == null) {
            binId++;
        }
        if (binId == table.length) {
            return;
        }
        current = table[binId];
    }
}
