package signal;

import linear.algebra.Vector;

public class DiscreteFourierTransform {
	Vector v;
	ComplexVector V;
	
	public DiscreteFourierTransform(Vector v) {
		this.v = v;
		this.V = dft();
	}

	/**
	 * Compute the Discrete Fourier Tansform of v
	 * V[k] = \sum_{m=0}^{n-1} v[m] e^{-2 \pi i \frac{m k}{n}}
	 */
	private ComplexVector dft() {
		ComplexVector dft = new ComplexVector(v.size());
		int n = this.v.size();
		for (int k = 0; k < n; k++) {
			dftCoefficient(dft, k);
		}
		return dft;
	}

	private void dftCoefficient(ComplexVector dft, int k) {
		int n = this.v.size();
		int realSum = 0;
		int imageSum = 0;
		for (int m = 0; m < n; m++) {
			double phase = - (2 * Math.PI * m * k) / n;
			realSum += this.v.v[m] * Math.cos(phase);
			imageSum += this.v.v[m] * Math.sin(phase);
		}
		dft.real.v[k] = realSum;
		dft.image.v[k] = imageSum;
	}
	
	/**
	 * @param harmonics number of frequencies to keep
	 * @param predictions number of additional samples to predict
	 * @return
	 */
	public Vector lowPassFilter(int harmonics, int predictions) {
		int n = this.v.size();
		final Vector frequencies = frequencies(n, 1f);
		final Vector indexes = indexesAbsAscending(n);
		
		int m = n+predictions;
		Vector filtered = new Vector(m);
		Vector time = Vector.range(m);
		for (int i = 0; i <= Math.min(2*harmonics, n-1); i++) {
			int index = (int) indexes.v[i];
			filtered = filtered.add(time.apply(harmonic(this.V.get(index), frequencies.v[index])));
		}
		
		return filtered;
	}

	private Vector.Transform harmonic(ComplexNumber harmonic, double frequency) {
		int n = this.v.size();
		double amplitude = harmonic.absolute()/n;
		double phase = harmonic.angle();
		
		return t -> amplitude * Math.cos(2 * Math.PI * frequency * t + phase);
	}

	/**
	 * @param n
	 * @return the indexes of the frequencies vector ordered by abs(frequency) ascending 
	 */
	private Vector indexesAbsAscending(int n) {
		double[] d = new double[n];
		d[0] = 0;
		int p = n/2;
		if (n % 2 == 1) {
			p++;
		}
		for (int i = 1; i < p; i++) {
			d[2*i-1] = i;
			d[2*i] = n-i;
		}
		if (n % 2 == 0) {
			d[n-1] = p;
		}
		return new Vector(d);
	}

	/**
	 * @param n
	 * @param spacing
	 * @return {@link Vector} the sample frequencies
	 */
	private Vector frequencies(int n, double spacing) {
		double[] d = new double[n];
		int p;
		
		p = ((n % 2) == 0) ? n/2-1 : (n-1)/2;
		for (int i = 0; i <= p; i++) {
			d[i] = i;
		}
		
		for (int i = p+1; i < n; i++) {
			d[i] = i-n;
		}
		
		return new Vector(d).divide(spacing*n);
	}
}
