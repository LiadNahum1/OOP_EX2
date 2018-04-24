package polynom;
import scalar.*;

public interface IPolynomial {
	Polynomial add(Polynomial poly);
	Polynomial mul(Polynomial poly);
	Scalar evaluate(Scalar scalar);
	Polynomial derivate();
	String toString();
	boolean equals(Polynomial poly);
}
