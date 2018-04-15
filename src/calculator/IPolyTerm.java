package calculator;

public interface IPolyTerm {
	boolean canAdd(PolyTerm pt);
	IPolyTerm add(PolyTerm pt);
	IPolyTerm mul(PolyTerm pt);
	Scalar evaluate(Scalar scalar);
	IPolyTerm derivate();
	boolean equals(PolyTerm pt);
	
	
}
