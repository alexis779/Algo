package org.tech.vineyard.hash;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map.Entry;

public class TableEntrySet<K, V> extends AbstractSet<Entry<K,V>> {

    private final ChainingHashMap<K,V> hashMap;

    public TableEntrySet(final ChainingHashMap<K,V> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new TableEntryIterator<K, V>(hashMap);
    }

    @Override
    public int size() {
        return hashMap.size();
    }

    @Override
    public boolean equals(Object object) {
        final TableEntrySet<K, V> other = (TableEntrySet<K, V>) object;
        if (size() != other.size()) {
            return false;
        }

        final Iterator<Entry<K, V>> iterator = iterator();
        final Iterator<Entry<K, V>> otherIterator = other.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().equals(otherIterator.next())) {
                return false;
            }
        }
        return true;
    }
}
