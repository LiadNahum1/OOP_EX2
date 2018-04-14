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

	public Scalar add(Scalar s) {
		// TODO Auto-generated method stub
		return null;
	}

	public Scalar mul(Scalar s) {
		// TODO Auto-generated method stub
		return null;
	}

	public Scalar neg() {
		// TODO Auto-generated method stub
		return null;
	}

	public Scalar inv() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean equals(Scalar s) {
		// TODO Auto-generated method stub
		return false;
	}

}
