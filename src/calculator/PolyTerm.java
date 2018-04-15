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

	public PolyTerm add(PolyTerm pt) {
		if (this.canAdd(pt))
		{
			Scalar addedCoeff = pt.getCoefficient().add(getCoefficient());
			return new PolyTerm(addedCoeff,this.exponent);
		}
		return null;
	}

	public PolyTerm mul(PolyTerm pt) {
		
		return new PolyTerm (this.coefficient.mul(pt.getCoefficient()),this.exponent+pt.getExponent());
	}

	public Scalar evaluate(Scalar scalar) {
		Scalar answer = scalar.inv().mul(scalar); //start the answer scalar at the value one in case the exponent is zero
		if(this.exponent >= 1) { //this will rise the scalar in the exponent
		 answer = scalar.mul(scalar);
		for(int i = 1 ; i < this.exponent ; i++) {
			answer = scalar.mul(answer);
		}
		}
		answer =  answer.mul(coefficient); 
		return answer;
	}

	public PolyTerm derivate() {
		Scalar newCoeff = this.coefficient.inv().add(coefficient);//start the new coefficient at zero in case the exponent is zero
		if(this.exponent >= 1) { //this will rise the scalar in the exponent
			 newCoeff = newCoeff.add(this.coefficient);//this will mul the coefficient in the exponent according to derivate lows
			for(int i = 1 ; i < this.exponent ; i++) {
				newCoeff = newCoeff.add(newCoeff);
			}
			return new PolyTerm (newCoeff , this.exponent - 1);
			}
		return new PolyTerm (newCoeff , 0);
	}

	public boolean equals(PolyTerm pt) {
		return  this.exponent == pt.getExponent() & this.coefficient.equals(pt.getCoefficient()); 
	}
	

}
