package linear.algebra.fitting;

import linear.algebra.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LinearRegressionTest {

	@Test
	public void linearRegression() throws Exception {
		int[] x = new int[] { 95, 85, 80, 70, 60 };
		int[] y = new int[] { 85, 95, 70, 65, 70 };
		LinearRegression linearRegression = new LinearRegression(1, new Vector(x), new Vector(y));
		
		assertEquals(78.29, linearRegression.fit(80), 0.01f);
	}

}
