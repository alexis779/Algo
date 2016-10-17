package linear.algebra.fitting;

import linear.algebra.Vector;

import org.junit.Assert;
import org.junit.Test;

public class TestLinearRegression {

	@Test
	public void linearRegression() throws Exception {
		int[] x = new int[] { 95, 85, 80, 70, 60 };
		int[] y = new int[] { 85, 95, 70, 65, 70 };
		LinearRegression linearRegression = new LinearRegression(1, new Vector(x), new Vector(y));
		
		Assert.assertEquals(78.29, linearRegression.fit(80), 0.01f);
	}

}
