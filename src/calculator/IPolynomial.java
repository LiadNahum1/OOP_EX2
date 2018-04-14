package calculator;

public interface IPolynomial {
	IPolynomial add(IPolynomial poly);
	IPolynomial mul(IPolynomial poly);
	Scalar evaluate(Scalar scalar);
	IPolynomial derivate();
	String toString();
	boolean equals(IPolynomial poly);
}
