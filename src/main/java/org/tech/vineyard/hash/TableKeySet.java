package org.tech.vineyard.hash;

import java.util.AbstractSet;
import java.util.Iterator;

public class TableKeySet<K, V> extends AbstractSet<K> {

    private final ChainingHashMap<K,V> hashMap;

    public TableKeySet(final ChainingHashMap<K,V> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public Iterator<K> iterator() {
        return new TableKeyIterator<>(new TableEntryIterator<>(hashMap));
    }

    @Override
    public int size() {
        return hashMap.size();
    }
}
