package org.tech.vineyard.linear.programming;

import org.junit.jupiter.api.Test;
import org.tech.vineyard.linear.programming.BranchAndBound;
import org.tech.vineyard.linear.programming.Constraint;
import org.tech.vineyard.linear.programming.Objective;
import org.tech.vineyard.linear.programming.Simplex;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class SimplexTest {

	private final static double DELTA = 0.0001d;

	/**
	 * https://en.wikipedia.org/wiki/Simplex_algorithm#Example
	 */
	@Test
	public void simplexCanonical() {
		double[] c = new double[]
				{ 2, 3, 4 };
		double[][] A = new double[][] {
				{ 3, 2, 1 },
				{ 2, 5, 3 }
		};
		double[] b = new double[] {
				10,
				15
		};
		Constraint[] constraints = new Constraint[] {
				Constraint.LOWER,
				Constraint.LOWER
		};

		Simplex simplex = new Simplex(c, A, b, constraints, Objective.MAX);

		double[] solution = simplex.solution();

		assertEquals(20, simplex.objectiveValue(), DELTA);
		assertArrayEquals(new double[] { 0, 0, 5 }, solution, DELTA);
	}

	/**
	 * https://en.wikipedia.org/wiki/Simplex_algorithm#Example_2
	 *
	 * Same system but with equalities.
	 */
	@Test
	public void simplexWithEqualityConstraint() {
		double[] c = new double[]
				{ 2, 3, 4 };
		double[][] A = new double[][] {
				{ 3, 2, 1 },
				{ 2, 5, 3 }
		};
		double[] b = new double[] {
				10,
				15
		};
		Constraint[] constraints = new Constraint[] {
				Constraint.EQUAL,
				Constraint.EQUAL,
		};

		Simplex simplex = new Simplex(c, A, b, constraints, Objective.MAX);

		double[] solution = simplex.solution();
		assertEquals(130d / 7, simplex.objectiveValue(), DELTA);
		assertArrayEquals(new double[] { 15d / 7, 0, 25d / 7 }, solution, DELTA);
	}

	/**
	 * https://www.codechef.com/MARCH21B/problems/COLGLF4
	 *
	 * System has no solution.
	 */
	@Test
	void collegeLife4Test1() {
		int N = 5;
		int E = 4;
		int H = 4;
		int A = 2;
		int B = 2;
		int C = 2;

		double[] c = new double[]
				{ A, B, C };
		double[][] a = new double[][] {
				{ 1, 1, 1 },
				{ 2, 0, 1 },
				{ 0, 3, 1 }
		};
		double[] b = new double[] {
				N,
				E,
				H
		};
		Constraint[] constraints = new Constraint[] {
				Constraint.EQUAL,
				Constraint.LOWER,
				Constraint.LOWER
		};

		assertThrows(RuntimeException.class, () -> {
			new Simplex(c, a, b, constraints, Objective.MIN);
		});
	}

	/**
	 * test case 2
	 *
	 * System has an integral optimal solution.
	 */
	@Test
	void collegeLife4Test2() {
		int N = 4;
		int E = 5;
		int H = 5;
		int A = 1;
		int B = 2;
		int C = 3;

		double[] c = new double[]
				{ A, B, C };
		double[][] a = new double[][] {
				{ 1, 1, 1 },
				{ 2, 0, 1 },
				{ 0, 3, 1 }
		};
		double[] b = new double[] {
				N,
				E,
				H
		};
		Constraint[] constraints = new Constraint[] {
				Constraint.EQUAL,
				Constraint.LOWER,
				Constraint.LOWER
		};

		Simplex simplex = new Simplex(c, a, b, constraints, Objective.MIN);
		assertEquals(11d / 2, simplex.objectiveValue());
		assertArrayEquals(new double[] { 5d / 2, 3d / 2, 0 }, simplex.solution());

		BranchAndBound branchAndBound = new BranchAndBound(simplex);
		assertEquals(7, branchAndBound.objectiveValue());
		assertArrayEquals(new int[] { 2, 1, 1 }, branchAndBound.solution());
	}

	/**
	 * test case 3
	 *
	 * Optimal solution of the relaxation problem is already integral.
	 */
	@Test
	void collegeLife4Test3() {
		int N = 4;
		int E = 5;
		int H = 5;
		int A = 3;
		int B = 2;
		int C = 1;

		double[] c = new double[]
				{ A, B, C };
		double[][] a = new double[][] {
				{ 1, 1, 1 },
				{ 2, 0, 1 },
				{ 0, 3, 1 }
		};
		double[] b = new double[] {
				N,
				E,
				H
		};
		Constraint[] constraints = new Constraint[] {
				Constraint.EQUAL,
				Constraint.LOWER,
				Constraint.LOWER
		};

		Simplex simplex = new Simplex(c, a, b, constraints, Objective.MIN);
		assertEquals(4, simplex.objectiveValue());
		assertArrayEquals(new double[] { 0, 0, 4 }, simplex.solution());

		BranchAndBound branchAndBound = new BranchAndBound(simplex);
		assertEquals(4, branchAndBound.objectiveValue());
		assertArrayEquals(new int[] { 0, 0, 4 }, branchAndBound.solution());
	}
}