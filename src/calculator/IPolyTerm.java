package calculator;

public interface IPolyTerm {
	boolean canAdd(IPolyTerm pt);
	IPolyTerm add(IPolyTerm pt);
	IPolyTerm mul(IPolyTerm pt);
	Scalar evaluate(Scalar scalar);
	IPolyTerm derivate();
	boolean equals(IPolyTerm pt);
	
	
}
