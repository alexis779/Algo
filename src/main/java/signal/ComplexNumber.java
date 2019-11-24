package signal;

public class ComplexNumber {
	double real;
	double image;
	
	double absolute;
	double angle;
	public ComplexNumber(double real, double image) {
		this.real = real;
		this.image = image;
		
		this.absolute = Math.sqrt(this.real*this.real + this.image*this.image);
		this.angle = Math.atan2(this.image, this.real);
	}
	
	public double absolute() {
		return this.absolute;
	}

	public double angle() {
		return this.angle;
	}
}
