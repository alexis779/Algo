package org.tech.vineyard.hash;

import java.util.AbstractSet;
import java.util.Iterator;

public class TableValueSet<K, V> extends AbstractSet<V> {

    private final ChainingHashMap<K,V> hashMap;

    public TableValueSet(final ChainingHashMap<K,V> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public Iterator<V> iterator() {
        return new TableValueIterator<>(new TableEntryIterator<>(hashMap));
    }

    @Override
    public int size() {
        return hashMap.size();
    }
}
