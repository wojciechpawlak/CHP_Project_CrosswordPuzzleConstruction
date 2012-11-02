package dk.dtu.imm.chp.cpc;

import java.util.Comparator;

public class SizeComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		int firstStringSize = ((String) o1).length();
		int secondStringSize = ((String) o2).length();
		
		if(firstStringSize > secondStringSize) {
			return 1;
		} else if (secondStringSize > firstStringSize) {
			return -1;
		} else {
			String s1 = (String) o1;
			String s2 = (String) o2;

			if(s1.compareTo(s2) > 0) return 1;
			else if (s1.compareTo(s2) < 0 ) return -1;
			else return 0;
		}
	}
	

}
