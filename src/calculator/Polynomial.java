package calculator;
import java.util.Iterator;
import java.util.Vector;

public class Polynomial  {
	private Vector <PolyTerm> polyTerms;

	/*constructor*/
	public Polynomial() {
		this.polyTerms = new Vector<PolyTerm>();
	}
	public Polynomial(String input) {
		this.polyTerms = new Vector<PolyTerm>();
		//if there is "-" sign we will add "+" before it so the program will be generic
		for(int i=0; i< input.length(); i = i+1) {
			if(input.charAt(i) == '-') {
				input = input.substring(0, i) + "+" + input.substring(i);
				i = i +1; 
			}
		}
		//we will split the input into PolyTerms according to "+" signs 
		String [] splitInput = input.split("+");
		for(int i=0; i< splitInput.length; i = i +1) {
			//TODO check capital x 
			//split every PolyTerm into scalar and exponent 
			String scalar;
			int exponent;
			if(splitInput[i].contains("x")) {
				String[]splitPolyTerm = splitInput[i].split("x");
				scalar = splitPolyTerm[0];
				exponent = Integer.parseInt(splitPolyTerm[1].substring(1));
			}
			//exponent = 0 
			else {
				scalar = splitInput[i];
				exponent = 0; 
			}
			
			Scalar s; 
			//checking if the scalar is a Real number
			if(scalar.contains(".")) {
				s = new RealScalar(Double.parseDouble(scalar));
			}
			else {
				//Rational number
				//checking if there is a "/" sign
				if(scalar.contains("/")) {
					s = new RationalScalar(Integer.parseInt(scalar.split("/")[0]), Integer.parseInt(scalar.split("/")[1]));
				}
				else {
					s= new RationalScalar(Integer.parseInt(scalar),1);
				}
			}
			//building the PolyTerm
			this.polyTerms.add(new PolyTerm(s, exponent));
			//TODO sort
			this.polyTerms.sort(new ExponentComparator());
		}
		
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
		Polynomial result = new Polynomial();
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
		return result; 
	}

	public Polynomial mul(Polynomial poly) {
		Polynomial result = new Polynomial();
		Vector<Polynomial> polynomialToSum = new Vector<Polynomial>(); 
		for(PolyTerm pt: this.polyTerms) {
			Polynomial polynom = new Polynomial(); 
			for(PolyTerm ptOther: poly.getPolyTerms()) {
				//in every polynomial that we create the PolyTerms are different in their exponents 
				polynom.addToPolyTermVector(pt.mul(ptOther));
			}
			polynomialToSum.add(polynom);
		}
		
		return result; 
	}

	public Scalar evaluate(Scalar scalar) {
		// TODO Auto-generated method stub
		return null;
	}

	public IPolynomial derivate() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean equals(IPolynomial poly) {
		// TODO Auto-generated method stub
		return false;
	}

}
