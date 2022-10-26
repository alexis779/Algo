package org.tech.vineyard.signal;

import org.tech.vineyard.linear.algebra.Vector;

public class ComplexVector {
	Vector real;
	Vector image;
	
	int size;
	
	public ComplexVector(int size) {
		this.size = size;
		this.real = new Vector(size);
		this.image = new Vector(size);
	}

	public void println() {
		double[] re = real.v;
		double[] im = image.v;
		for (int i = 0; i < this.size; i++) {
			System.out.print(re[i] + " " +  im[i] + "i ");
		}
		System.out.println();
	}

	public int size() {
		return this.size;
	}

	public ComplexNumber get(int i) {
		return new ComplexNumber(this.real.v[i], this.image.v[i]);
	}
}
