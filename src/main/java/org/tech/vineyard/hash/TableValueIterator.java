package org.tech.vineyard.hash;

import java.util.Iterator;
import java.util.Map.Entry;

public class TableValueIterator<K, V> implements Iterator<V> {

    private TableEntryIterator<K, V> iterator;
    public TableValueIterator(final TableEntryIterator<K, V> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public V next() {
        final Entry<K, V> entry = iterator.next();
        return entry.getValue();
    }
}
