package polynom;
import scalar.*;
import java.util.Iterator;
import java.util.Vector;

public class Polynomial  {
	private Vector <PolyTerm> polyTerms;
	private boolean isReal; 
	private ExponentComparator comparator; 

	/*constructors*/
	public Polynomial(boolean isReal) {
		this.polyTerms = new Vector<PolyTerm>();
		this.isReal = isReal; 
		this.comparator = new ExponentComparator(); 
	}
	public Polynomial(String input, boolean isReal) { 
		this.polyTerms = new Vector<PolyTerm>();
		this.isReal = isReal; 
		this.comparator = new ExponentComparator(); 

		String []splitInput = splitIntoPolyTerms(input);
		for(int i=0; i< splitInput.length; i = i +1) {
			String [] scalarAndExpo = extractScalarAndExpo(splitInput[i]).split(" ");
			String scalar = scalarAndExpo[0];
			String exponent = scalarAndExpo[1]; 

			//Do not add PolyTerms that their scalars are 0 
			if(!scalar.equals("0") & !scalar.equals("-0")) {
				//building Scalar instance according to whether the client chose Rational or Real
				Scalar s = buildingScalar(scalar); 
				//building the PolyTerm
				this.polyTerms.add(new PolyTerm(s, Integer.parseInt(exponent)));
			}
		}
		//if the input includes only zeroes 
		if(this.polyTerms.isEmpty()) {
			this.polyTerms = zeroPolynomial().getPolyTerms();
		}
		this.polyTerms.sort(this.comparator);
	}

	/*The function split the String data that describes Polynomial into String array that describes its PolyTerms */  
	private String []splitIntoPolyTerms(String input){
		//if there is "-" sign in string input we will add "+" before it so the program will be generic
		for(int i=1; i< input.length(); i = i+1) {
			if(input.charAt(i) == '-') {
				input = input.substring(0, i) + "+" + input.substring(i);
				i = i + 1; 
			}
		}
		//we will split the input into PolyTerms according to "+" signs 
		String [] splitInput = input.split("\\+");
		return splitInput; 

	}
	/*The function returns a String includes the scalar and the exponent of the PolyTerm according to several situations*/ 
	private String extractScalarAndExpo(String poly) {
		String scalar;
		String exponent; 
		if(poly.contains("x")) {
			//The input is only "x" which means scalar == 1 and exponent == 1 
			if(poly.length()==1) {
				scalar = "1";
				exponent = "1";
			}
			//The input is kx which means exponent == 1
			else if(poly.charAt(poly.length()-1)== 'x') {
				scalar = poly.substring(0, poly.length()-1);
				if(scalar.equals("-"))
					scalar = scalar + "1"; 
				exponent = "1";
			}
			//The input is x^k which means scalar == 1
			else if(poly.charAt(0)=='x') {
				scalar = "1";
				exponent = poly.substring(2);
			}

			//The input is px^k which means both scalar and exponent are explicitly written in the input  
			else {
				int index = poly.indexOf('x');
				scalar = poly.substring(0,index);
				if(scalar.equals("-"))
					scalar = scalar + "1"; 
				exponent = poly.substring(index+2);
			}
		}
		// the PolyTerm doesn't include x which means exponent = 0 
		else {
			scalar = poly;
			exponent = "0"; 
		}
		return scalar + " " + exponent; 
	}
	/*The function crate a Scalar instance according to isReal value*/
	private Scalar buildingScalar(String scalar) {
		Scalar s;
		if(this.isReal) {
			//Real number
			s = new RealScalar(scalar);
		}
		else {
			//Rational number
			s= new RationalScalar(scalar);
		}
		return s; 
	}
	/*The function returns a polynomial that is 0*/
	private Polynomial zeroPolynomial() {
		Polynomial zero = new Polynomial(this.isReal);
		Scalar onlyInVector; 
		if(this.isReal) {
			onlyInVector = new RealScalar(0);
		}
		else
			onlyInVector = new RationalScalar(0,1);
		zero.getPolyTerms().add(new PolyTerm(onlyInVector, 0));
		return zero; 
	}
	/*getters and setters*/
	public boolean getIsReal() {
		return this.isReal;
	}
	public void setIsReal(boolean isReal) {
		this.isReal = isReal;  
	}
	public Vector<PolyTerm> getPolyTerms(){
		return this.polyTerms;
	}
	public void setPolyTerms(Vector<PolyTerm> ptList) {
		this.polyTerms = ptList;
		this.polyTerms.sort(this.comparator);
	}
	/*The function add to Polynomial another PolyTerm and sort its PolyTerms vector
	 * The function DO NOT add a Polyterm  that its scalar is zero*/
	public void addPolyTermToVector(PolyTerm pt) {
		if(!pt.getCoefficient().toString().equals("0")) {
			this.polyTerms.add(pt);
			this.polyTerms.sort(this.comparator);
		}
	}
	/*receives a Polynomial and returns a Polynomial which is the sum of the current Polynomial with the argument
	 * assumes both Polynomials- current and argument- are valid therefore, when find a match between two PolyTerms
	 * (we can add them) there is no another PolyTerm in argument that we can add to this PolyTerm of the current Polynomial*/
	public Polynomial add(Polynomial poly) {
		//if one of the Polynomial is 0 
		if(equals(zeroPolynomial())) {
			return poly; 
		}
		if(poly.equals(zeroPolynomial())) {
			return this; 
		}
		Polynomial result = new Polynomial(this.isReal);
		Vector<PolyTerm> other = poly.getPolyTerms(); 
		Iterator<PolyTerm> currentPolyIt = getPolyTerms().iterator();
		while(currentPolyIt.hasNext()) {
			PolyTerm addToResult = currentPolyIt.next();
			Iterator<PolyTerm> otherPolyIt = other.iterator();
			boolean isFound = false;
			while(otherPolyIt.hasNext() & !isFound) {
				PolyTerm otherTerm = otherPolyIt.next();
				if(addToResult.canAdd(otherTerm)) {
					addToResult = addToResult.add(otherTerm); 
					isFound = true;
					other.remove(otherTerm);
				}
			}
			result.addPolyTermToVector(addToResult);
		}
		//adding the argument's PolyTerms that couldn't be added with the current's PolyTerms
		for(PolyTerm otherPoly: other) {
			result.addPolyTermToVector(otherPoly);
		}
		//sort
		result.getPolyTerms().sort(this.comparator);
		return result; 
	}
	/*receives a Polynomial and returns a Polynomial is the multiplication of the current Polynomial with the argument
	 * The function multiples every PolyTerm in current Polynomial with all the PolyTerms of argument and create a polynomial
	 * that includes all the PolyTerms that created in the multiplication. This is a valid Polynomial. Then do that to every PolyTerm of current
	 * and then, sum up all the Polynomials that we have created into one Polynomial*/
	public Polynomial mul(Polynomial poly) {
		Polynomial result = new Polynomial(this.isReal);
		//if one of the Polynomial is 0 
		if(equals(zeroPolynomial())|poly.equals(zeroPolynomial())) {
			return zeroPolynomial(); 
		}
		Vector<Polynomial> polynomialToSum = new Vector<Polynomial>(); 
		for(PolyTerm pt: getPolyTerms()) {
			Polynomial polynom = new Polynomial(this.isReal); 
			for(PolyTerm ptOther: poly.getPolyTerms()) {
				//in every polynomial that we create the PolyTerms are different in their exponents 
				polynom.addPolyTermToVector(pt.mul(ptOther));
			}
			if(!polynom.getPolyTerms().isEmpty())
				polynomialToSum.add(polynom);
		}

		//sum up all the polynomial received from the multiple 
		Iterator<Polynomial> iterator = polynomialToSum.iterator();
		while(iterator.hasNext()) {
			result = result.add(iterator.next()); 
		}
		//sort
		result.getPolyTerms().sort(this.comparator);
		return result; 
	}
	/*evaluates the polynomial using the argument scalar*/
	public Scalar evaluate(Scalar scalar) {
		//initialize the result 
		Scalar result = buildingScalar("0"); 
		for(PolyTerm pt: this.polyTerms) {
			result = result.add(pt.evaluate(scalar)); 
		}
		return result; 
	}
	/*returns the Polynomial which is the result of applying first order derivation on the current Polynomial*/ 
	public Polynomial derivate() {
		Polynomial result = new Polynomial(this.isReal);
		for(PolyTerm pt: getPolyTerms()) {
			//add only derivation that 
			result.addPolyTermToVector(pt.derivate());
		}
		if(result.getPolyTerms().isEmpty()) {
			result = zeroPolynomial();
		}
		return result; 
	}
	public String toString() {
		String str = "";
		for(PolyTerm pt : getPolyTerms()) {
			if(!pt.getCoefficient().toString().contains("-")) {
				str= str + "+";
			}
			str = str + pt.toString();
		}
		//remove + from start of the string 
		if(str.charAt(0)=='+')
			str= str.substring(1);
		return str; 
	}
	/*returns true if the argument polynomial is equal to the current polynomial*/
	public boolean equals(Polynomial poly) {
		for(PolyTerm currentPt: getPolyTerms()) {
			for(PolyTerm otherPt: poly.getPolyTerms()) {
				if(!currentPt.equals(otherPt))
					return false; 
			}
		}
		return true;
	}

}
