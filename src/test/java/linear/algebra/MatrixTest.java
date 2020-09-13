package linear.algebra;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatrixTest extends AbstractMatrixTest {

    public double[][] m() {
        return new double[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
    }

    /**
     * @return Same matrix as above, without row = 1 and column = 1
     */
    public double[][] subM() {
        return new double[][] {
                {1, 3},
                {7, 9}
        };
    }

    @Test
    public void deleteRowColumn() {
        Matrix m = new Matrix(m());
        assertSameMatrix(m.deleteRowColumn(1, 1), subM());
    }

    @Test
    public void determinant() {
        double[][] data = subM();
        double determinant = data[0][0]*data[1][1] - data[1][0]*data[0][1];
        Matrix m = new Matrix(data);
        Assertions.assertEquals(determinant, new CramerRule().determinant(m));
    }

    /**
     * https://en.wikipedia.org/wiki/System_of_linear_equations#Cramer's_rule
     */
    public double[][] A() {
        return new double[][] {
                {1, 3, -2},
                {3, 5, 6},
                {2, 4, 3}
        };
    }

    public double[] b() {
        return new double[]
                {5, 7, 8};
    }

    public double[] x() {
        return new double[]
                {-15, 8, 2};
    }

    @Test
    public void linearEquationSystem() {
        Matrix A = new Matrix(A());
        Vector b = new Vector(b());

        LinearEquationSystem cramerRule = new CramerRule();
        assertSameRow(cramerRule.solution(A, b), x());

        LinearEquationSystem luDecomposition = new LUDecomposition();
        assertSameRow(luDecomposition.solution(A, b), x());
    }
}
