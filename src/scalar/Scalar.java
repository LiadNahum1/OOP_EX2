package scalar;

public interface Scalar {
	Scalar add(Scalar s);
	Scalar mul(Scalar s);
	Scalar neg();
	Scalar inv();
	boolean equals(Scalar s); 
	String toString();

}
