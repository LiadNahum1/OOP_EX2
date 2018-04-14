package calculator;

/*The class implements Scalar for Rational numbers*/
public class RationalScalar implements Scalar{
	private int a;
	private int b; //b!=0
	
	public RationalScalar(int a, int b) {
		if(b!=0) {
			this.a = a;
			this.b = b;
		}
		else {
			throw new IllegalArgumentException(); 
		}
	}
	
	public int getA() {
		return this.a; 
	}
	
	public int getB() {
		return this.b; 
	}
	
	public void setValue(int a, int b) {
		if(b!=0) {
			this.a = a;
			this.b = b;
		}
		else {
			throw new IllegalArgumentException(); 
		}
	}
	
	/*accepts a scalar argument and returns a scalar which is the sum of the current scalar and the argument*/ 
	public Scalar add(Scalar s) {
		return new RationalScalar(getA()*((RationalScalar)s).getB() + getB()*((RationalScalar)s).getA(), ((RationalScalar)s).getB());
	}
	/*accepts a scalar argument and returns a scalar which is the multiplication of the current scalar and
	 * the argument*/ 
	public Scalar mul(Scalar s) {
		return new RationalScalar(getA() * ((RationalScalar)s).getA(), ((RationalScalar)s).getB());
	}

	/*returns a scalar which is the result of the multiplying the current scalar by (-1)*/
	public Scalar neg() {
		return mul(new RationalScalar(-1,1));
	}

	/*returns (1/scalar)*/
	public Scalar inv() {
		return new RationalScalar(getB(), getA());
	}

	/*returns true if the argument Scalar and the current Scalar have the same numeric value*/
	public boolean equals(Scalar s) {
		if(getA()/ getB() == ((RationalScalar)s).getA()/((RationalScalar)s).getB())
			return true;
		return false; 
	}
	
	public String toString() {
		String str = "" + getA();
		if(b!=1)
			str = str + "/" + getB(); 
		return str; 
	}

}
