package calculator;

/*The class implements Scalar for Real numbers*/
public class RealScalar implements Scalar{
	private double value; 
	public RealScalar(double value) {
		this.value = value; 
	}

	/*return value of scalar*/
	public double getValue() {
		return this.value; 
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	/*accepts a scalar argument and returns a scalar which is the sum of the current scalar and the argument*/ 
	public Scalar add(Scalar s) {
		return new RealScalar(getValue() + ((RealScalar)s).getValue());
	}
	/*accepts a scalar argument and returns a scalar which is the multiplication of the current scalar and
	 * the argument*/ 
	public Scalar mul(Scalar s) {
		return new RealScalar(getValue() * ((RealScalar)s).getValue());
	}

	/*returns a scalar which is the result of the multiplying the current scalar by (-1)*/
	public Scalar neg() {
		return mul(new RealScalar(-1));
	}

	/*returns (1/scalar)*/
	public Scalar inv() {
		if(getValue() != 0.0) {
			return new RealScalar(1/getValue());
		}
		else
			throw new IllegalArgumentException();
	}

	/*returns true if the argument Scalar and the current Scalar have the same numeric value*/
	public boolean equals(Scalar s) {
		if(getValue() == ((RealScalar)s).getValue())
			return true;
		return false; 
	}
	
	/* Real numbers are printed up to 3 digits*/ 
	@Override
	public String toString() {
		double value = getValue();
		int round = (int)(value* 1000);
		value = ((double)round)/1000; 
		if (value >= 0)
			return "+" + value;
		else
			return "" + value;
	}

}
