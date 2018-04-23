package polinom;
import java.util.Comparator;
public class ExponentComparator implements Comparator<PolyTerm> {

	@Override
	public int compare(PolyTerm o1, PolyTerm o2) {
		if(o1.getExponent() > o2.getExponent())
			return 1;
		if(o1.getExponent() < o2.getExponent())
			return -1;
		return 0;
	}

}
