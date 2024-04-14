package org.tech.vineyard.set.multiset;

import java.util.Map;
import java.util.HashMap;
public class MultiSet<T> {

    private final Map<T, Integer> counts = new HashMap<>();

    private int size = 0;

    public int count(T t) {
        return counts.getOrDefault(t, 0);
    }

    public int add(T t) {
        return add(t, 1);
    }

    public int remove(T t) {
        if (! contains(t)) {
            return 0;
        }
        int count = add(t, -1);
        if (count == 0) {
            counts.remove(t);
        }
        return count;
    }

    public boolean contains(T t) {
        return counts.containsKey(t);
    }

    private int add(T t, int delta) {
        size += delta;
        return counts.merge(t, delta, Integer::sum);
    }

    public int size() {
        return size;
    }

    public MultiSet<T> intersection(MultiSet<T> bSet) {
        MultiSet<T> result = new MultiSet<>();

        bSet.counts.entrySet()
            .stream()
            .filter(entry -> contains(entry.getKey()))
            .forEach(entry -> result.add(entry.getKey(), Math.min(counts.get(entry.getKey()),
                                                                  entry.getValue())));

        return result;
    }

    public String toString() {
        return String.format("%d: %s", size, counts);
    }
}
