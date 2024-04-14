package org.tech.vineyard.set.multiset;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiSetTest {
    @Test
    public void test() {
        String a = "a";
        String b = "b";

        MultiSet<String> multiSet = new MultiSet<>();
        multiSet.add(a);
        multiSet.add(b);
        multiSet.add(a);
        multiSet.add(a);

        assertTrue(multiSet.contains(b));
        assertEquals(3, multiSet.count(a));
    }    
}
