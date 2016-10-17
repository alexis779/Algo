package linear.algebra.interpolation;

import org.junit.Assert;
import org.junit.Test;

public class TestPolyFit {

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
		Assert.assertEquals(8.91, lagrange.fit(x0), 0.01f);
		
		PolyFit linearLeastSquares = windowSelector.linearLeastSquaresInterpolation();
		Assert.assertEquals(8.91, linearLeastSquares.fit(x0), 0.01f);
		
		PolyFit cubicSpline = new CubicSplineInterpolation(x(), y());
		Assert.assertEquals(8.6, cubicSpline.fit(x0), 0.1f);
	}
}
