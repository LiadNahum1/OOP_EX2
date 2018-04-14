package calculator;
import java.util.Iterator;
import java.util.Vector;

public class Polynomial  {
	private Vector <PolyTerm> polyTerms;
	private boolean isReal; 

	/*constructor*/
	public Polynomial(boolean isReal) {
		this.polyTerms = new Vector<PolyTerm>();
		this.isReal = isReal; 
	}
	public Polynomial(String input, boolean isReal) {
		this.polyTerms = new Vector<PolyTerm>();
		//if there is "-" sign in string input we will add "+" before it so the program will be generic
		for(int i=0; i< input.length(); i = i+1) {
			if(input.charAt(i) == '-') {
				input = input.substring(0, i) + "+" + input.substring(i);
				i = i + 1; 
			}
		}
		//we will split the input into PolyTerms according to "+" signs 
		String [] splitInput = input.split("+");
		for(int i=0; i< splitInput.length; i = i +1) {
			//TODO do to string to lower case 
			
			//split every PolyTerm into scalar and exponent 
			String scalar;
			String exponent;
			if(splitInput[i].contains("x")) {
				String[]splitPolyTerm = splitInput[i].split("x");
				//scalar == 1 and exponent == 1 
				if(splitPolyTerm.length == 0) {
					scalar = "1";
					exponent = "1";
				}
				//scalar == 1 or exponent == 1 
				if(splitPolyTerm.length == 1 ) {
					if(splitInput[i].charAt(0)== 'x') {
						//if true then scalar == 1 
						scalar = "1";
						exponent = splitPolyTerm[0];
					}
					else {
						scalar = splitPolyTerm[0];
						exponent = "1";
					}
				}
				//both scalar and exponent are explicitly written in the input  
				else {
					scalar = splitPolyTerm[0];
					exponent = splitPolyTerm[1].substring(1);
				}
			
			}
			// the PolyTerm doesn't include x which means exponent = 0 
			else {
				scalar = splitInput[i];
				exponent = "0"; 
			}
			
			Scalar s; 
			//checking if the scalar is a Real number
			if(scalar.contains(".")) {
				s = new RealScalar(Double.parseDouble(scalar));
			}
			else {
				//Rational number
				//checking if there is a "/" sign
				//TODO check if needed
				if(scalar.contains("/")) {
					s = new RationalScalar(Integer.parseInt(scalar.split("/")[0]), Integer.parseInt(scalar.split("/")[1]));
				}
				else {
					s= new RationalScalar(Integer.parseInt(scalar),1);
				}
			}
			//building the PolyTerm
			this.polyTerms.add(new PolyTerm(s, Integer.parseInt(exponent)));
		}
		this.polyTerms.sort(new ExponentComparator());
		this.isReal = isReal; 
		
	}
	public Vector<PolyTerm> getPolyTerms(){
		return this.polyTerms;
	}
	public void setPolyTerms(Vector<PolyTerm> ptList) {
		this.polyTerms = ptList;
		//TODO sort
		this.polyTerms.sort(new ExponentComparator());
	}
	public void addToPolyTermVector(PolyTerm pt) {
		this.polyTerms.add(pt);
		//TODO sort
		this.polyTerms.sort(new ExponentComparator());
	}

	/*receives a Polynomial and returns a Polynomial which is the sum of the current Polynomial with the argument*/
	public Polynomial add(Polynomial poly) {
		Polynomial result = new Polynomial(this.isReal);
		Vector<PolyTerm> other = poly.getPolyTerms(); 
		Iterator<PolyTerm>polyIt = getPolyTerms().iterator();
		while(polyIt.hasNext()) {
			PolyTerm addToResult = polyIt.next();
			Iterator<PolyTerm>polyOtherIt = other.iterator();
			boolean isFound = false;
			while(polyOtherIt.hasNext() & !isFound) {
				PolyTerm otherTerm = polyOtherIt.next();
				if(addToResult.canAdd(otherTerm)) {
					addToResult = addToResult.add(otherTerm);
					isFound = true;
					other.remove(otherTerm);
				}
			}
			result.addToPolyTermVector(addToResult);	
		}
		for(PolyTerm otherPoly: other) {
			result.addToPolyTermVector(otherPoly);
		}
		
		//sort
		result.getPolyTerms().sort(new ExponentComparator());
		return result; 
	}
	/*receives a Polynomial and returns a Polynomial is the multiplication of the current Polynomial with the argument*/
	public Polynomial mul(Polynomial poly) {
		Polynomial result = new Polynomial(this.isReal);
		Vector<Polynomial> polynomialToSum = new Vector<Polynomial>(); 
		for(PolyTerm pt: this.polyTerms) {
			Polynomial polynom = new Polynomial(this.isReal); 
			for(PolyTerm ptOther: poly.getPolyTerms()) {
				//in every polynomial that we create the PolyTerms are different in their exponents 
				polynom.addToPolyTermVector(pt.mul(ptOther));
			}
			polynomialToSum.add(polynom);
		}
	
		//sum up all the polynomial received from the multiple 
		Iterator<Polynomial> iterator = polynomialToSum.iterator();
		if(iterator.hasNext()) {
			result = result.add(iterator.next());
		}
		while(iterator.hasNext()) {
			result = result.add(iterator.next()); 
		}
		//sort
		result.getPolyTerms().sort(new ExponentComparator());
		return result; 
	}
	/*evaluates the polynomial using the argument scalar*/
	public Scalar evaluate(Scalar scalar) {
		Scalar result; 
		if(this.isReal){
			result = new RealScalar(0);
		}
		else {
			result = new RationalScalar(0,1);
		}
		for(PolyTerm pt: this.polyTerms) {
			result = result.add(pt.evaluate(scalar)); 
		}
		return result; 
	}
	/*returns the Polynomial which is the result of applying first order derivation on the current Polynomial*/ 
	public Polynomial derivate() {
		Polynomial result = new Polynomial(this.isReal);
		for(PolyTerm pt: getPolyTerms()) {
			result.addToPolyTermVector(pt.derivate());
		}
		return result; 
	}
	
	public String toString() {
		String str = "";
		for(PolyTerm pt : getPolyTerms()) {
			str = str + pt.toString();
		}
		return str; 
	}
	
	/*returns true if the argument polynomial is equal to the current polynomial*/
	public boolean equals(Polynomial poly) {
		poly.getPolyTerms().sort(new ExponentComparator());
		for(PolyTerm currentPt: getPolyTerms()) {
			for(PolyTerm otherPt: poly.getPolyTerms()) {
				if(!currentPt.equals(otherPt))
					return false; 
			}
		}
		return true;
	}
	
}
