package sort;

import com.google.common.collect.ImmutableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class BinarySearchTest {
    private static final List<Integer> L = ImmutableList.of(1, 2, 3, 4, 7, 7 ,9, 14);

    private BinarySearch<Integer> binarySearch = new BinarySearch<>();

    @Test
    public void binarySearch() {
        assertEquals(2, binarySearch.search(L, 3), "index of the element when present");

        assertEquals(5, binarySearch.search(L, 7), "right most position by default when element is present");
        assertEquals(4, binarySearch.leftMostSearch(L, 7), "left most position when element is present");
        assertEquals(5, binarySearch.rightMostSearch(L, 7), "right most position when element is present");

        assertEquals(6, binarySearch.search(L, 8), "Insertion Point when element is missing");
        assertEquals(6, binarySearch.leftMostSearch(L, 8), "Insertion Point via Left Most when element is missing");
        assertEquals(6, binarySearch.rightMostSearch(L, 8), "Insertion Point via Right Most when element is missing");

        assertEquals(0, binarySearch.search(L, 0), "element lower that min should be inserted at the beginnin");
        assertEquals(8, binarySearch.search(L, 15), "element greater than max should be added at the end");
    }

    @Test
    public void binarySearchCollections() {
        assertEquals(-7, Collections.binarySearch(L, 8), "- Insertion Point - 1 by convention");
        assertEquals(2, Collections.binarySearch(L, 3), "index of the element when present");
        assertEquals(5, Collections.binarySearch(L, 7), "index of an element when multiple match");
    }

    @Test
    public void duplicates() {
        final List<Integer> d = ImmutableList.of(6, 6, 6);
        assertEquals(1, Collections.binarySearch(d, 6));
        assertEquals(2, binarySearch.search(d, 6));
    }

}