package org.tech.vineyard.hash;

import java.util.Iterator;
import java.util.Map.Entry;

public class TableKeyIterator<K, V> implements Iterator<K> {

    private TableEntryIterator<K, V> iterator;
    public TableKeyIterator(final TableEntryIterator<K, V> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public K next() {
        final Entry<K, V> entry = iterator.next();
        return entry.getKey();
    }
}
