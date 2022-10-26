package org.tech.vineyard.sort;

import java.util.List;

/**
 * Binary Search on a sorted list.
 *
 * @param <T> a Comparable class
 */
public class BinarySearch<T extends Comparable<T>> {

    /**
     *
     * @param l a sorted list
     * @param t the element to find
     * @return the index of element t in the list l
     */
    public int search(List<T> l, T t) {
        return rightMostSearch(l, t);
    }

    /**
     *
     * @param l a sorted list
     * @param t the element to find
     * @return the left-most index of element t in the list l
     */
    public int leftMostSearch(List<T> l, T t) {
        int start = 0;
        int end = l.size()-1;

        while (start <= end) {
            int mid = (start + end) / 2;
            if (l.get(mid).compareTo(t) < 0) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return start;
    }

    /**
     *
     * @param l a sorted list
     * @param t the element to find
     * @return the right-most index of element t in the list l
     */    public int rightMostSearch(List<T> l, T t) {
        int start = 0;
        int end = l.size()-1;

        while (start <= end) {
            int mid = (start + end) / 2;
            if (l.get(mid).compareTo(t) <= 0) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        if (end == -1 || l.get(end).compareTo(t) < 0) {
            return end+1;
        }
        return end;
    }
}
