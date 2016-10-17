package linear.programming;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestSimplex {

	/**
	 * https://en.wikipedia.org/wiki/Simplex_algorithm#Example_2
	 */
	//@Test
	public void simplex1() {
		double[] c = new double[] {-2, -3, -4};
		double[][] A = new double[][] {
			{3, 2, 1},
			{2, 5, 3}
		};
		double[] b = new double[] {10, 15};
		
		Simplex simplex = new Simplex(c, A, b);
		
		double[] expected = new double[] {15f/7, 0, 25f/7};
		double[] solution = simplex.solution();
		
		assertEquals(expected.length, solution.length);
		for (int j = 0; j < expected.length; j++) {
			assertEquals(expected[j], solution[j], 0.0001f);
		}
	}

	@Test
	public void simplex2() {
		double[] c = new double[] {3, 2, 5};
		double[][] A = new double[][] {
			{1, 0, -1},
			{-2, 1, -1},
			{2, 1, 2}
		};
		double[] b = new double[] {5, 2, 3};
		
		Simplex simplex = new Simplex(c, A, b);
		
		//double[] expected = new double[] {15f/7, 0, 25f/7};
		double[] solution = simplex.solution();
		for (int j = 0; j < solution.length; j++) {
			//assertEquals(expected[j], solution[j], 0.0001f);
			System.out.print(solution[j] + " ");
		}
		System.out.println();
		assertEquals(39f, simplex.objectiveFunction(), 0.0001f);

	}

}
