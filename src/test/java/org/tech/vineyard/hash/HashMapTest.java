package org.tech.vineyard.hash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class HashMapTest {

    @Test
    public void testHashMap() {
        final String key = "Paris";
        final Double value = 12.7;

        Optional.of(key);

        final Map<String, Double> hashMap = new ChainingHashMap<>(8);
        assertNull(hashMap.get(key));

        hashMap.put(key, value);
        assertEquals(value, hashMap.get(key));

        final List<Double> values = hashMap.values()
            .stream()
            .collect(Collectors.toList());

        assertEquals(List.of(value), values);

        hashMap.remove(key);
        assertNull(hashMap.get(key));
    }
}
