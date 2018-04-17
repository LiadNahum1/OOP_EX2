package calculator;
import java.util.Iterator;
import java.util.Vector;

public class Polynomial  {
	private Vector <PolyTerm> polyTerms;
	private boolean isReal; 
	private ExponentComparator comparator; 

	/*constructor*/
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
			//TODO do to string to lower case
			String [] scalarAndExpo = extractScalarAndExpo(splitInput[i]).split(" ");
			String scalar = scalarAndExpo[0];
			String exponent = scalarAndExpo[1]; 

			//building Scalar instance according to whether the client chose Rational or Real
			Scalar s = buildingScalar(scalar); 
	
			//building the PolyTerm
			this.polyTerms.add(new PolyTerm(s, Integer.parseInt(exponent)));
		}
		this.polyTerms.sort(new ExponentComparator());
	}
	
	/*The function split the String data that describes Polynomial into string data that describes PolyTerms*/  
	private String []splitIntoPolyTerms(String input){
		//if there is "-" sign in string input we will add "+" before it so the program will be generic
		for(int i=0; i< input.length(); i = i+1) {
			if(input.charAt(i) == '-') {
				input = input.substring(0, i) + "+" + input.substring(i);
				i = i + 1; 
			}
		}
		//we will split the input into PolyTerms according to "+" signs 
		String [] splitInput = input.split("+");
		return splitInput; 
		
	}
	/*The function returns a string includes the scalar and the exponent of the PolyTerm according to several situations*/ 
	private String extractScalarAndExpo(String poly) {
		String scalar;
		String exponent; 
		if(poly.contains("x")) {
			String[]splitPolyTerm = poly.split("x");
			
			//The input is only "x" which means scalar == 1 and exponent == 1 
			if(splitPolyTerm.length == 0) {
				scalar = "1";
				exponent = "1";
			}
			
			//The input is x^k or kx wich means scalar == 1 or exponent == 1 
			if(splitPolyTerm.length == 1 ) {
				if(poly.charAt(0)== 'x') {
					//if true then scalar == 1 
					scalar = "1";
					exponent = splitPolyTerm[0];
				}
				else {
					scalar = splitPolyTerm[0];
					if(scalar == "0")
						exponent = "0";
					else
						exponent = "1";
				}
			}
					
			//The input is px^k which means both scalar and exponent are explicitly written in the input  
			else {
				scalar = splitPolyTerm[0];
				if(scalar == "0")
					exponent = "0";
				else
					exponent = splitPolyTerm[1].substring(1);
			}
		}
		
		// the PolyTerm doesn't include x which means exponent = 0 
		else {
			scalar = poly;
			exponent = "0"; 
		}
		return scalar + " " + exponent; 
	}
	
	private Scalar buildingScalar(String scalar) {
		Scalar s;
		if(this.isReal) {
			if(scalar.contains("/")) {
				s = new RealScalar(Double.parseDouble(scalar.split("/")[0])/Double.parseDouble(scalar.split("/")[1]));
			}
			else
				s = new RealScalar(Double.parseDouble(scalar));			
		}
		else {
			//Rational number
			if(scalar.contains("/")) {
				s = new RationalScalar(Integer.parseInt(scalar.split("/")[0]), Integer.parseInt(scalar.split("/")[1]));
			}
			else {
				s= new RationalScalar(Integer.parseInt(scalar),1);
			}
		}
		return s; 
	}
	
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
	public void addPolyTermToVector(PolyTerm pt) {
		this.polyTerms.add(pt);
		this.polyTerms.sort(this.comparator);
	}

	/*receives a Polynomial and returns a Polynomial which is the sum of the current Polynomial with the argument*/
	public Polynomial add(Polynomial poly) {
		Polynomial result = new Polynomial(this.isReal);
		poly.getPolyTerms().sort(this.comparator);
		Vector<PolyTerm> other = poly.getPolyTerms(); 
		Iterator<PolyTerm>currentPolyIt = getPolyTerms().iterator();
		while(currentPolyIt.hasNext()) {
			PolyTerm addToResult = currentPolyIt.next();
			Iterator<PolyTerm> otherPolyIt = other.iterator();
			boolean isFound = false;
			while(otherPolyIt.hasNext() & !isFound) {
				PolyTerm otherTerm = otherPolyIt.next();
				if(addToResult.canAdd(otherTerm)) {
					addToResult = addToResult.add(otherTerm);
					isFound = true;
					//TODO check
					other.remove(otherTerm);
				}
			}
			result.addPolyTermToVector(addToResult);	
		}
		for(PolyTerm otherPoly: other) {
			result.addPolyTermToVector(otherPoly);
		}
		
		//sort
		result.getPolyTerms().sort(this.comparator);
		return result; 
	}
	/*receives a Polynomial and returns a Polynomial is the multiplication of the current Polynomial with the argument*/
	public Polynomial mul(Polynomial poly) {
		Polynomial result = new Polynomial(this.isReal);
		Vector<Polynomial> polynomialToSum = new Vector<Polynomial>(); 
		for(PolyTerm pt: getPolyTerms()) {
			Polynomial polynom = new Polynomial(this.isReal); 
			for(PolyTerm ptOther: poly.getPolyTerms()) {
				//in every polynomial that we create the PolyTerms are different in their exponents 
				polynom.addPolyTermToVector(pt.mul(ptOther));
			}
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
			result.addPolyTermToVector(pt.derivate());
		}
		return result; 
	}
	
	public String toString() {
		String str = "";
		for(PolyTerm pt : getPolyTerms()) {
			str = str + pt.toString();
		}
		if(str=="")
			str = "0";
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
