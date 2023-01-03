package org.tech.vineyard.recurrence;

/**
 * Hilbert curve is an example of a space-filling curve.
 * It is a fractal that maps a 2D set to a 1D set.
 */
public class HilbertCurve {

    /**
     * Power of 2 which is the size of the square containing the Hilbert curve.
     */
    private final int maxn;

    /**
     *
     * @param maxn the hilbert curve max iteration
     */
    public HilbertCurve(int maxn) {
        this.maxn = maxn;
    }

    /**
     * See <a href="https://en.wikipedia.org/wiki/Hilbert_curve#Applications_and_mapping_algorithms">...</a>
     *
     * @param x x coordinate of a 2D point, {@literal 0 <= x < maxn}
     * @param y y coordinate of a 2D point, {@literal 0 <= y < maxn}
     * @return the index i on Hilbert curve of (x, y), {@literal 0 <= i < maxn^2}
     */
    public long hilbertCurveOrder(int x, int y) {
        long d = 0;
        for (int s = maxn >> 1; s > 0; s >>= 1) {
            int rx = x & s, ry = y & s;
            d += 1L * s * s * ((3 * rx) ^ ry);
            if (ry == 0) {
                if (rx != 0) {
                    x = maxn-1 - x;
                    y = maxn-1 - y;
                }
                int tmp = x;
                x = y;
                y = tmp;
            }
        }
        return d;
    }
}
