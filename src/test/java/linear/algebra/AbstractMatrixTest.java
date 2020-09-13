package linear.algebra;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class AbstractMatrixTest {

    protected void assertSameMatrix(Matrix a, double[][] expected) {
        assertSameMatrix(a.M, expected);
    }

    protected void assertSameMatrix(double[][] v, double[][] expected) {
        for (int i = 0; i < expected.length; i++) {
            assertSameRow(v[i], expected[i]);
        }
    }

    protected void assertSameRow(Vector v, double[] expected) {
        assertSameRow(v.v, expected);
    }

    protected void assertSameRow(double[] actual, double[] expected) {
        assertArrayEquals(expected, actual, 0.001f);
    }
}
