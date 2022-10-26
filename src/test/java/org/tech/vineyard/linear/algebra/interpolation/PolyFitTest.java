package org.tech.vineyard.linear.algebra.interpolation;

import org.junit.jupiter.api.Test;
import org.tech.vineyard.linear.algebra.interpolation.CubicSplineInterpolation;
import org.tech.vineyard.linear.algebra.interpolation.PolyFit;
import org.tech.vineyard.linear.algebra.interpolation.WindowSelector;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PolyFitTest {

	private double[] x() {
		return new double[] {0, 1, 2, 5, 7, 8, 9, 10, 11};
	}

	private double[] y() {
		return new double[] {5, 7.3, 6.2, 17.7, 17.5, 16.3, 14.6, 9.6, 5.8};
	}

	@Test
	public void interpolation() throws Exception {
		double x0 = 3;
		
		// degree is 3 for a polynom interpolating 4 points 
		WindowSelector windowSelector = new WindowSelector(x(), y(), 4);
		windowSelector.setX0(x0);
		
		PolyFit lagrange = windowSelector.lagrangeInterpolation();
		assertEquals(8.91, lagrange.fit(x0), 0.01f);
		
		PolyFit linearLeastSquares = windowSelector.linearLeastSquaresInterpolation();
		assertEquals(8.9, linearLeastSquares.fit(x0), 0.1f);
		
		PolyFit cubicSpline = new CubicSplineInterpolation(x(), y());
		assertEquals(8.6, cubicSpline.fit(x0), 0.1f);
	}
}
