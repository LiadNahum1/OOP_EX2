package polynom;
import scalar.*;

public class PolyTerm implements IPolyTerm{
	Scalar coefficient;
	int exponent;

	/*constructor*/
	public PolyTerm(Scalar coefficient,int exponent) {
		this.coefficient = coefficient;
		this.exponent = exponent;
	}
	public Scalar getCoefficient() {
		return this.coefficient;
	}
	public void setCoefficient(Scalar coefficient) {
		this.coefficient = coefficient;
	}
	public int getExponent() {
		return this.exponent;
	}
	public void setExponent(int exponent) {
		this.exponent = exponent;
	}
	
	/*receives a PolyTerm and returns true if the argument PolyTerm can be added 
	 * to the current PolyTerm(same power)*/
	public boolean canAdd(PolyTerm pt) {
		return pt.getExponent() == this.exponent;
	}
	/*receives a PolyTerm and returns a new PolyTerm which is the result of adding the current 
	 * PolyTerm and the argument*/
	public PolyTerm add(PolyTerm pt) {
		if (canAdd(pt))
		{
			Scalar addedCoeff = pt.getCoefficient().add(getCoefficient());
			return new PolyTerm(addedCoeff,this.exponent);
		}
		return null;
	}
	/*receives a PolyTerm and returns a new PolyTerm which is the resukt of multiplying 
	 * the current PolyTerm and the argument*/ 
	public PolyTerm mul(PolyTerm pt) {
		if(this.coefficient.mul(pt.getCoefficient()).toString().equals("0")) {
			return new PolyTerm (this.coefficient.mul(pt.getCoefficient()),0);
		}
		else
			return new PolyTerm (this.coefficient.mul(pt.getCoefficient()),this.exponent+pt.getExponent());
	}
	/*evaluates the current term using the scalar*/
	public Scalar evaluate(Scalar scalar) {
		Scalar answer = getCoefficient();
		if(this.exponent >= 1) { //this will rise the scalar in the exponent
			answer = getCoefficient().mul(scalar);
			for(int i = 1 ; i < this.exponent ; i++) {
				answer = scalar.mul(answer);
			}
		}
		return answer;
	}
	/*returns the PolyTerms which is the result of the derivations on the current PolyTerm*/
	public PolyTerm derivate() {
		Scalar newCoeff = getCoefficient().neg().add(coefficient);//start the new coefficient at zero in case the exponent is zero
		if(this.exponent >= 1) { //this will rise the scalar in the exponent
			newCoeff = newCoeff.add(getCoefficient());//this will mul the coefficient in the exponent according to derivate lows
			for(int i = 1 ; i < this.exponent ; i++) {
				newCoeff = newCoeff.add(getCoefficient());
			}
			return new PolyTerm (newCoeff , this.exponent - 1);
		}
		return new PolyTerm (newCoeff , 0);
	}

	public boolean equals(PolyTerm pt) {
		return  this.exponent == pt.getExponent() & getCoefficient().equals(pt.getCoefficient()); 
	}
	@Override
	public String toString() {
		if(getExponent() == 0)
			return coefficient.toString();
		if(getCoefficient().toString().equals("1") | getCoefficient().toString().equals("1.0"))
			return "x^" + getExponent(); 
		return coefficient.toString() + "x^" + getExponent();
	}


}
