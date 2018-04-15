package calculator;

public class PolyTerm implements IPolyTerm{
	Scalar coefficient;
	int exponent;

	public PolyTerm(Scalar coefficient,int exponent) {
		this.coefficient = coefficient;
		this.exponent = exponent;
	}
	public Scalar getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Scalar coefficient) {
		this.coefficient = coefficient;
	}

	public int getExponent() {
		return exponent;
	}

	public void setExponent(int exponent) {
		this.exponent = exponent;
	}

	public boolean canAdd(PolyTerm pt) {
		return pt.getExponent() == this.exponent;
	}

	public IPolyTerm add(PolyTerm pt) {
		if (this.canAdd(pt))
		{
			int newCoefficien = pt.getCoefficient().getNumber()+this.getCoefficient().getNumber();
	//	  Scalar added = new  (coefficient);
		}
		return null;
	}

	public IPolyTerm mul(PolyTerm pt) {
		// TODO Auto-generated method stub
		return null;
	}

	public Scalar evaluate(Scalar scalar) {
		// TODO Auto-generated method stub
		return null;
	}

	public IPolyTerm derivate() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean equals(PolyTerm pt) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
